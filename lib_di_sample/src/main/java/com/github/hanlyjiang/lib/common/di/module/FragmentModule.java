package com.github.hanlyjiang.lib.common.di.module;

import com.github.hanlyjiang.lib.common.di.instance.TestDiFragment;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface FragmentModule {

    @ContributesAndroidInjector
    TestDiFragment contributeTestDiFragment();
}
