package com.github.hanlyjiang.lib.common.di.framework.module;

import com.github.hanlyjiang.lib.common.di.test.TestDiBroadcastReceiver;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * BroadcastReceiverModule
 * @author hanlyjiang on 2022/3/6-12:04 AM
 * @version 1.0
 */
@Module
public interface BroadcastReceiverModule {

    @ContributesAndroidInjector
    TestDiBroadcastReceiver ContributeTestDiBroadcastReceiver();
}
