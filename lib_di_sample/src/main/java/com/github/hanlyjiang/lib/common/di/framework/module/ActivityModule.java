package com.github.hanlyjiang.lib.common.di.framework.module;

import com.github.hanlyjiang.lib.common.di.test.TestDiMainActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ActivityModule {

    @ContributesAndroidInjector
    TestDiMainActivity contributeTestDiMainActivity();

//    @ContributesAndroidInjector
//    TestDiMvpActivity contributeTestDiMvpActivity();
}
