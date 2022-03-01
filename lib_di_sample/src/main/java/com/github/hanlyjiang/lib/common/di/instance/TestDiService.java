package com.github.hanlyjiang.lib.common.di.instance;

import android.content.Intent;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;
import com.github.hanlyjiang.lib.common.di.Injectable;
import com.github.hanlyjiang.lib.common.di.SdkAndroidInjection;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;

public class TestDiService extends JobIntentService implements Injectable {

    @Inject
    TestSingleton testSingleton;

    @Inject
    TestObj testObj;

    public TestDiService() {
    }

    @Override
    public void onCreate() {
        SdkAndroidInjection.inject(this);
        super.onCreate();
    }

    @Override
    public int onStartCommand(@Nullable @org.jetbrains.annotations.Nullable Intent intent, int flags, int startId) {
        Log.d(getClass().getSimpleName(), "testSingleton == null: " + (testSingleton == null));
        Log.d(getClass().getSimpleName(), "testObj == null: " + (testObj == null));
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    protected void onHandleWork(@NonNull @NotNull Intent intent) {

    }

}