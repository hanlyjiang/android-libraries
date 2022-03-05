package com.github.hanlyjiang.lib.common.di.framework.module;

import com.github.hanlyjiang.lib.common.di.test.TestDiFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface FragmentModule {

    @ContributesAndroidInjector
    TestDiFragment contributeTestDiFragment();
}
