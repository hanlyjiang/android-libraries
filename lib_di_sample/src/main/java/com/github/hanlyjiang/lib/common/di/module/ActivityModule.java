package com.github.hanlyjiang.lib.common.di.module;

import com.github.hanlyjiang.lib.common.di.instance.TestDiMainActivity;
import com.github.hanlyjiang.lib.common.di.instance.TestDiMvpActivity;
import com.github.hanlyjiang.lib.common.di.scope.ActivityScope;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public interface ActivityModule {

    @ContributesAndroidInjector
    TestDiMainActivity contributeTestDiMainActivity();

//    @ContributesAndroidInjector
//    TestDiMvpActivity contributeTestDiMvpActivity();
}
