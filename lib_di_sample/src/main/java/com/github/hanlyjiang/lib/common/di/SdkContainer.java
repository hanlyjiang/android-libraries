package com.github.hanlyjiang.lib.common.di;

import com.github.hanlyjiang.lib.common.di.instance.TestObj;
import com.github.hanlyjiang.lib.common.di.instance.TestSingleton;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasAndroidInjector;

import javax.inject.Inject;

/**
 * SDK 容器
 */
public class SdkContainer implements HasAndroidInjector {

    @Inject
    DispatchingAndroidInjector<Object> androidInjector;

    @Inject
    TestSingleton testSingleton;

    @Inject
    TestObj testObj;

    public void testInject() {
        assert testSingleton != null;
        assert testObj != null;
    }

    @Override
    public AndroidInjector<Object> androidInjector() {
        return androidInjector;
    }

}
