package com.github.hanlyjiang.lib.common.di.framework;

import com.github.hanlyjiang.lib.common.di.test.TestObj;
import com.github.hanlyjiang.lib.common.di.test.TestSingleton;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

import javax.inject.Inject;

/**
 * SDK 容器
 */
public class SdkContainer implements HasAndroidInjector, IDiContainer {

    private SdkComponent sdkComponent;

    @Inject
    protected DispatchingAndroidInjector<Object> androidInjector;

    @Inject
    protected TestSingleton testSingleton;

    @Inject
    protected TestObj testObj;

    public SdkContainer(SdkComponent sdkComponent) {
        this.sdkComponent = sdkComponent;
    }

    public void testInject() {
        assert testSingleton != null;
        assert testObj != null;
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }

    @Override
    public void destory() {

    }

    public SdkComponent getSdkComponent() {
        return sdkComponent;
    }
}
