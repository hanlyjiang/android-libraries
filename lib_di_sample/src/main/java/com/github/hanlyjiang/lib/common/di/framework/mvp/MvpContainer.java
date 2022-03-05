package com.github.hanlyjiang.lib.common.di.framework.mvp;

import android.app.Activity;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.github.hanlyjiang.lib.common.di.framework.IDiContainer;
import com.github.hanlyjiang.lib.common.di.framework.module.MvpModule;
import com.github.hanlyjiang.lib.common.di.framework.scope.MvpScope;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

import javax.inject.Inject;

/**
 * MVP 的 Android组件DI容器
 *
 * @author hanlyjiang on 2022/3/5-11:03 PM
 * @version 1.0
 */
@MvpScope
public class MvpContainer implements HasAndroidInjector , IDiContainer {

    protected MvpModule.MvpComponent mvpComponent;

    /**
     * Provider Activity
     * 之所以使用 @Nullable 标记，是因为我们公用了一个组件用于提供这Fragment注入和Activity注入，
     * 而实际上我们同一时间只需要一个（要么Activity，要么Fragment），所以必然有一个为null，而对于
     * Dagger来说，组件的builder传递的参数必须用@Nullable标记，且所有使用到（需要被注入）的地方都
     * 需要使用 @Nullable标记
     * （下面 fragmentProvider 同）
     */
    @Nullable
    @Inject
    protected AndroidProvider<Activity> activityProvider;

    /**
     * Provider Fragment
     * 之所以使用 @Nullable 标记，是因为我们公用了一个组件用于提供这Fragment注入和Activity注入
     */
    @Nullable
    @Inject
    protected AndroidProvider<Fragment> fragmentProvider;

    /**
     * Dagger-Android 提供的，用于存储 Activity 的注入器
     */
    @Inject
    protected DispatchingAndroidInjector<Object> androidInjector;

    public MvpContainer(MvpModule.MvpComponent mvpComponent) {
        this.mvpComponent = mvpComponent;
    }

    /**
     * {@link com.github.hanlyjiang.lib.common.di.framework.SdkAndroidInjection SdkAndroidInjection }
     * 中使用此方法来获取对应的Android组件注入器
     *
     * @return AndroidInjector<Object>
     */
    @Override
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }

    @Override
    public void destory() {
        this.mvpComponent = null;
    }
}
