package com.github.hanlyjiang.lib.common.di.framework.module;

import com.github.hanlyjiang.lib.common.di.test.TestDiService;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
* Service 注册
* @author hanlyjiang on 2022/3/5-11:17 PM
* @version 1.0
*/
@Module
public interface ServiceModule {

    @ContributesAndroidInjector
    TestDiService contributeTestDiService();
}
