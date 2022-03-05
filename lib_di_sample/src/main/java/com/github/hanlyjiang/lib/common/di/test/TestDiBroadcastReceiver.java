package com.github.hanlyjiang.lib.common.di.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.github.hanlyjiang.lib.common.di.framework.SdkAndroidInjection;

import javax.inject.Inject;

/**
 * TestDiBroadcastReceiver
 * @author hanlyjiang on 2022/3/5-11:59 PM
 * @version 1.0
 */
public class TestDiBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION = "TestDiBroadcastReceiver.Action";

    @Inject
    TestSingleton testSingleton;

    @Inject
    TestObj testObj;

    @Override
    public void onReceive(Context context, Intent intent) {
        SdkAndroidInjection.inject(this, context);
        Test.assertInject(this, testSingleton, testObj);
    }
}
