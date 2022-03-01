package com.github.hanlyjiang.lib.common.di.module;

import com.github.hanlyjiang.lib.common.di.instance.TestDiService;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ServiceModule {

    @ContributesAndroidInjector
    TestDiService contributeTestDiService();
}
