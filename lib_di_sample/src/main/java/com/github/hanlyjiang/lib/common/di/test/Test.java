package com.github.hanlyjiang.lib.common.di.test;

import android.util.Log;

public class Test {

    public static void assertInject(Object callTarget, TestSingleton testSingleton, TestObj testObj) {
        Log.d(callTarget.getClass().getSimpleName(), "testSingleton == null: "
                + (testSingleton == null) + ", instance =  " + testSingleton);
        Log.d(callTarget.getClass().getSimpleName(), "testObj       == null: "
                + (testObj == null) + ", instance =  " + testObj);
    }
}
