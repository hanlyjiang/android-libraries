package cn.hanlyjiang.hjapf.hook.activity;

import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;

import java.lang.reflect.Method;

import cn.hanlyjiang.apf_library.proxy.MethodDelegate;
import cn.hanlyjiang.apf_library.proxy.ProxyUtil;
import cn.hanlyjiang.apf_library.utils.LogUtil;
import cn.hanlyjiang.apf_library.utils.RefInvoker;

/**
 * 对 ActivityThread 中的 H(Handler)中的mCallback进行Hook
 * <br/>
 * 新版本的Activity的启动没有经过mCallback？还是可以调用了，需要自己设置callback进去。 新版本的Activity相关的MSG变化了，变成了一个通用的
 *
 * @author hanlyjiang 5/21/21 4:37 PM
 * @version 1.0
 */
public class HmCallbackHook extends MethodDelegate implements Handler.Callback {

    public static void install() {
        // 获取当前activityThread对象
        Object activityThread = ActivityThreadRef.currentActivityThread();
        Object mH = ActivityThreadRef.mH(activityThread);
        Object mCallback = ActivityThreadRef.mCallback(mH);
        Object mCallbackProxy = null;
        if (mCallback == null) {
            // hook时可能为null，我们直接创建一个Callback并赋值
            mCallbackProxy = new HmCallbackHook();
        } else {
            // hook时有值，则进行代理
            mCallbackProxy = ProxyUtil.createProxy(mCallback, new HmCallbackHook());
        }
        ActivityThreadRef.setmCallback(mH, mCallbackProxy);
        LogUtil.d("安装完成");
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        LogUtil.d("handleMessage");
        int what = msg.what;
        return false;
    }

    @Override
    public Object beforeInvoke(Object target, Method method, Object[] args) {
        return super.beforeInvoke(target, method, args);
    }

    @Override
    public Object afterInvoke(Object target, Method method, Object[] args, Object beforeInvoke, Object invokeResult) {
        return super.afterInvoke(target, method, args, beforeInvoke, invokeResult);
    }

    private static class ActivityThreadRef {

        private static final String ClassName = "android.app.ActivityThread";
        private static final String Method_currentActivityThread = "currentActivityThread";
        public static final String Field_ActivityThread_mH = "mH";
        public static final String Field_Handler_mCallback = "mCallback";

        public static Object currentActivityThread() {
            return RefInvoker.invokeStaticMethod(ClassName, Method_currentActivityThread);
        }

        public static Object mH(Object activityThread) {
            return RefInvoker.getField(activityThread, activityThread.getClass(), Field_ActivityThread_mH);
        }

        public static Object mCallback(Object mH) {
            return RefInvoker.getField(mH, Handler.class, Field_Handler_mCallback);
        }

        public static void setmCallback(Object mH, Object callback) {
            RefInvoker.setField(mH, Handler.class, Field_Handler_mCallback, callback);
        }
    }


}
