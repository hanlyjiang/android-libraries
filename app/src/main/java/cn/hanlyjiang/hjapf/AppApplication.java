package cn.hanlyjiang.hjapf;

import android.app.Application;

import cn.hanlyjiang.apf_library.utils.LogUtil;
import cn.hanlyjiang.hjapf.hook.activity.ActivityInstrumentationHook;
import cn.hanlyjiang.hjapf.hook.activity.ActivityManagerHook;
import cn.hanlyjiang.hjapf.hook.activity.HmCallbackHook;

/**
 * Application 入口
 *
 * @author hanlyjiang 5/21/21 1:39 PM
 * @version 1.0
 */
public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtil.setEnable(BuildConfig.DEBUG);

//        ActivityInstrumentationHook.install(this);
//        ActivityManagerHook.install();
        HmCallbackHook.install();
    }

}
