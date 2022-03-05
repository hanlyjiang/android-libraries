package com.github.hanlyjiang.lib.common.di.framework.module;

import com.github.hanlyjiang.lib.common.di.test.TestObj;
import com.github.hanlyjiang.lib.common.di.test.TestSingleton;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

@Module
public abstract class NormalObjModule {

    @Singleton
    @Provides
    static TestSingleton provideTestSingleton() {
        return new TestSingleton();
    }

    @Provides
    static TestObj providerTestOjb() {
        return new TestObj();
    }
}
