package com.github.hanlyjiang.lib.common.di.module;

import com.github.hanlyjiang.lib.common.di.instance.TestDiMainActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ActivityModule {

    @ContributesAndroidInjector
    TestDiMainActivity contributeTestDiMainActivity();
}
