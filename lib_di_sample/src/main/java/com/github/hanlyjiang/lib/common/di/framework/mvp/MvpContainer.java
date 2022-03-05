package com.github.hanlyjiang.lib.common.di.framework.mvp;

import android.app.Activity;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.github.hanlyjiang.lib.common.di.framework.mvp.AndroidProvider;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

import javax.inject.Inject;
import javax.inject.Provider;

/**
 * MVP 的 Android组件DI容器
 *
 */
public class MvpContainer implements HasAndroidInjector {

    @Nullable
    @Inject
    protected AndroidProvider<Activity> activityProvider;

    @Nullable
    @Inject
    protected AndroidProvider<Fragment> fragmentProvider;

    @Inject
    protected DispatchingAndroidInjector<Object> androidInjector;

    public MvpContainer() {
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }

    public Provider<Activity> getActivityProvider() {
        return activityProvider;
    }

    public AndroidProvider<Fragment> getFragmentProvider() {
        return fragmentProvider;
    }
}
