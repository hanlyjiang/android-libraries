package com.github.hanlyjiang.lib.common.di.framework;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.github.hanlyjiang.lib.common.di.framework.module.MvpModule;
import com.github.hanlyjiang.lib.common.di.framework.mvp.AndroidProvider;
import com.github.hanlyjiang.lib.common.di.framework.mvp.MvpContainer;
import com.github.hanlyjiang.lib.common.di.framework.mvp.MvpInjectable;
import org.jetbrains.annotations.NotNull;

/**
 * SDKInjector
 * 负责依赖注入的初始化工作，同时注册自动的Activity和Fragment的注入工作
 * Service 及 广播接收器的需要手动调用 {@link  SdkAndroidInjection#inject(Service)}
 * |  {@link  SdkAndroidInjection#inject(ContentProvider)} }
 * |  {@link  SdkAndroidInjection#inject(BroadcastReceiver, Context)} }
 */
public class SdkInjector {

    private static final String TAG = "SdkInjector";

    private static SdkContainer sSdkContainer;

    public static SdkComponent getSdkComponent() {
        return sSdkContainer.getSdkComponent();
    }

    public static SdkContainer getSdkContainer() {
        return sSdkContainer;
    }

    /**
     * 初始化
     *
     * @param application Application
     */
    public static void init(Application application) {
        if (sSdkContainer != null) {
            return;
        }
        SdkComponent sSdkComponent = DaggerSdkComponent.builder()
                .application(application)
                .build();
        SdkInjector.sSdkContainer = new SdkContainer(sSdkComponent);
        sSdkComponent.inject(sSdkContainer);
        sSdkContainer.testInject();
        application.registerActivityLifecycleCallbacks(new BasicActivityLifeCycleCallbacks() {

            @Override
            public void onActivityCreated(@NonNull @NotNull Activity activity, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
                super.onActivityCreated(activity, savedInstanceState);
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    injectActivity(activity, savedInstanceState);
                }
            }

            @Override
            public void onActivityPreCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.P) {
                    injectActivity(activity, savedInstanceState);
                }
                super.onActivityPreCreated(activity, savedInstanceState);
            }
        });
    }

    private static void injectActivity(@NotNull Activity activity, @Nullable Bundle savedInstanceState) {
        if (activity instanceof MvpInjectable) {
            MvpModule.MvpComponent mvpActivityComponent = SdkInjector.getSdkComponent()
                    .mvpComponentBuilder().activityProvider(new AndroidProvider<>(activity)).build();
            MvpContainer mvpContainer = new MvpContainer(mvpActivityComponent);
            mvpActivityComponent.inject(mvpContainer);
            SdkAndroidInjection.inject(activity, mvpContainer);
        } else if (activity instanceof Injectable) {
            SdkAndroidInjection.inject(activity);
        } else {
            Log.w(TAG, "Warning! You Activity " + activity.getClass().getSimpleName() + "is not injectable! ");
        }
        if (activity instanceof FragmentActivity) {
            final FragmentManager.FragmentLifecycleCallbacks lifecycleCallbacks = new FragmentManager.FragmentLifecycleCallbacks() {
                @Override
                public void onFragmentAttached(@NonNull @NotNull FragmentManager fm, @NonNull @NotNull Fragment f, @NonNull @NotNull Context context) {
                    super.onFragmentAttached(fm, f, context);
                    injectFragment(f, activity);
                }
            };
            ((FragmentActivity) activity).getSupportFragmentManager()
                    .registerFragmentLifecycleCallbacks(lifecycleCallbacks, true);
        }
    }

    private static void injectFragment(@NotNull Fragment f, @NotNull Activity activity) {
        if (f instanceof Injectable) {
            SdkAndroidInjection.inject(f);
        } else if (f instanceof MvpInjectable) {
            MvpModule.MvpComponent mvpComponent = SdkInjector.getSdkComponent()
                    .mvpComponentBuilder()
                    .activityProvider(new AndroidProvider<>(activity))
                    .fragmentProvider(new AndroidProvider<>(f))
                    .build();
            MvpContainer mvpContainer = mvpComponent.inject(new MvpContainer(mvpComponent));
            SdkAndroidInjection.inject(f, mvpContainer);
        } else {
            Log.w(TAG, "Warning! You fragment  " + f.getClass().getSimpleName() + " is not injectable!");
        }
    }
}
