package com.github.hanlyjiang.lib.common.di;

import android.app.Activity;
import android.app.Application;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentProvider;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import com.github.hanlyjiang.lib.common.di.base.BasicActivityLifeCycleCallbacks;
import org.jetbrains.annotations.NotNull;

/**
 * SDKInjector
 * 负责依赖注入的初始化工作，同时注册自动的Activity和Fragment的注入工作
 * Service 及 广播接收器的需要手动调用 {@link  SdkAndroidInjection#inject(Service)}
 * |  {@link  SdkAndroidInjection#inject(ContentProvider)} }
 * |  {@link  SdkAndroidInjection#inject(BroadcastReceiver, Context)} }
 */
public class SdkInjector {

    private static SdkComponent sSdkComponent;
    private static SdkContainer sSdkContainer;

    public static SdkComponent getSdkComponent() {
        return sSdkComponent;
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
        sSdkComponent = DaggerSdkComponent.builder()
//                .application(application)
                .build();
        SdkInjector.sSdkContainer = new SdkContainer();
        sSdkComponent.inject(sSdkContainer);
        sSdkContainer.testInject();
        application.registerActivityLifecycleCallbacks(new BasicActivityLifeCycleCallbacks() {
            @Override
            public void onActivityPreCreated(@NonNull @NotNull Activity activity, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
                injectActivity(activity, savedInstanceState);
                super.onActivityPreCreated(activity, savedInstanceState);
            }
        });
    }

    /**
     * 销毁
     */
    public static void destory() {
        sSdkComponent = null;
        sSdkContainer = null;
    }

    private static void injectActivity(@NotNull Activity activity, @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        if (activity instanceof Injectable) {
            SdkAndroidInjection.inject(activity);
        }
        if (activity instanceof FragmentActivity) {
            ((FragmentActivity) activity).getSupportFragmentManager()
                    .registerFragmentLifecycleCallbacks(
                            new FragmentManager.FragmentLifecycleCallbacks() {
                                @Override
                                public void onFragmentCreated(FragmentManager fm, Fragment f, Bundle savedInstanceState) {
                                    super.onFragmentCreated(fm, f, savedInstanceState);
                                    if (f instanceof Injectable) {
                                        SdkAndroidInjection.inject(f);
                                    }
                                }
                            }, true
                    );
        }
    }
}
