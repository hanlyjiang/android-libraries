package com.github.hanlyjiang.lib.common.di.framework.module;

import com.github.hanlyjiang.lib.common.di.test.TestObj;
import dagger.Module;
import dagger.Provides;


@Module
public abstract class NormalObjModule {

    @Provides
    static TestObj providerTestOjb() {
        return new TestObj();
    }
}
