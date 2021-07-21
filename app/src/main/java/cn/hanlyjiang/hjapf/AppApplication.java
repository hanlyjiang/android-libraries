package cn.hanlyjiang.hjapf;

import android.app.Activity;
import android.app.Application;
import android.content.res.Configuration;
import android.util.Log;

import androidx.annotation.NonNull;

import com.github.hanlyjiang.lib.common.helper.AppStatusChangeManager;
import com.github.hanlyjiang.lib.common.iface.OnAppStatusCallback;
import com.github.hanlyjiang.lib.common.utils.LogUtil;

import org.jetbrains.annotations.NotNull;


/**
 * Application 入口
 *
 * @author hanlyjiang 5/21/21 1:39 PM
 * @version 1.0
 */
public class AppApplication extends Application {

    private static final String TAG = "AppApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        LogUtil.setEnable(BuildConfig.DEBUG);
        AppStatusChangeManager.INSTANCE.registryAppStatusCallback(new OnAppStatusCallback() {
            @Override
            public void onAppToForeground(@NotNull Activity currentActivity) {
                Log.d(TAG, "onAppToForeground");
            }

            @Override
            public void onAppToBackground(@NotNull Activity currentActivity) {
                Log.d(TAG, "onAppToBackground");
            }

            @Override
            public void onTrimMemory(int level) {
                Log.d(TAG, "onTrimMemory: " + level);
            }

            @Override
            public void onConfigurationChanged(@NonNull Configuration newConfig) {
                Log.d(TAG, "onConfigurationChanged");
            }

            @Override
            public void onLowMemory() {
                Log.d(TAG, "onLowMemory");
            }
        });


//        ActivityInstrumentationHook.install(this);
//        ActivityManagerHook.install();
//        HmCallbackHook.install();
    }

}
