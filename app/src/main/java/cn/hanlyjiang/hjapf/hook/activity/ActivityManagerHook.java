package cn.hanlyjiang.hjapf.hook.activity;

import android.os.Build;

import com.github.hanlyjiang.lib.common.utils.LogUtil;
import com.github.hanlyjiang.lib.common.utils.RefInvoker;

import java.lang.reflect.Method;

import cn.hanlyjiang.apf_library.proxy.HackSingleton;
import cn.hanlyjiang.apf_library.proxy.MethodDelegate;
import cn.hanlyjiang.apf_library.proxy.ProxyUtil;


/**
 * Hook ActivityManager
 *
 * @author hanlyjiang 5/21/21 2:15 PM
 * @version 1.0
 */
public class ActivityManagerHook extends MethodDelegate {
    // 6.0 上有调用到此方法，11上很少方法能够进入hook
    public static final String METHOD_START_ACTIVITY = "startActivity";

    /**
     * 安装AMN代理
     */
    public static void install() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.N_MR1) {
            Object gDefault = AMNgDefault.getDefault();
            Object amProxy = ProxyUtil.createProxy(gDefault, new ActivityManagerHook());
            Object singleton = AMNgDefault.getGDefault();
            // 如果是IActivityManager
            if (singleton.getClass().isAssignableFrom(amProxy.getClass())) {
                AMNgDefault.setGDefault(amProxy);
            } else {
                // 否则是包装过的单例
                new HackSingleton(singleton).setInstance(amProxy);
            }
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.Q) {
            Object gDefault = AMNgDefault.getDefault();
            Object amProxy = ProxyUtil.createProxy(gDefault, new ActivityManagerHook());
            // Android O 没有gDefault这个成员了, 变量被移到了ActivityManager这个类中
            // 且现在startActivity不走startActivity这个方法了
            Object singleton = HackActivityManager.getIActivityManagerSingleton();
            if (singleton != null) {
                new HackSingleton(singleton).setInstance(amProxy);
            } else {
                LogUtil.e("WTF!!");
            }
        } else {
            // Android 30 可直接使用ActivityManager.getService()，因为后续AMN类会被删除
            Object rawActivityService = HackActivityManager.getService();
            Object amProxy = ProxyUtil.createProxy(rawActivityService, new ActivityManagerHook());
            Object singleton = HackActivityManager.getIActivityManagerSingleton();
            if (singleton != null) {
                new HackSingleton(singleton).setInstance(amProxy);
            } else {
                LogUtil.e("WTF!!");
            }
        }
        LogUtil.d("安装完成!!");
    }

    @Override
    public Object beforeInvoke(Object target, Method method, Object[] args) {
        LogUtil.v("beforeInvoke", method.getName());
        if (METHOD_START_ACTIVITY.equals(method.getName())) {
            LogUtil.d("Before Invoke: " + method.getName());
        }
        return super.beforeInvoke(target, method, args);
    }

    @Override
    public Object afterInvoke(Object target, Method method, Object[] args, Object beforeInvoke, Object invokeResult) {
        LogUtil.v("beforeInvoke", method.getName());
        // 早起版本走startActivity方法
        if (METHOD_START_ACTIVITY.equals(method.getName())) {
            LogUtil.d("After Invoke: " + method.getName());
        }
        return super.afterInvoke(target, method, args, beforeInvoke, invokeResult);
    }


    private static class AMNgDefault {

        private static final String ClassName = "android.app.ActivityManagerNative";
        private static final String Method_getDefault = "getDefault";
        private static final String Field_gDefault = "gDefault";

        public static void setGDefault(Object gDefault) {
            RefInvoker.setField(null, ClassName, Field_gDefault, gDefault);
        }

        public static Object getDefault() {
            return RefInvoker.invokeMethod(null, ClassName, Method_getDefault, (Class[]) null, (Object[]) null);
        }

        public static Object getGDefault() {
            return RefInvoker.getField(null, ClassName, Field_gDefault);
        }
    }

    public static class HackActivityManager {

        private static final String ClassName = "android.app.ActivityManager";

        private static final String Field_IActivityManagerSingleton = "IActivityManagerSingleton";
        private static final String Method_getService = "getService";

        public static Object getIActivityManagerSingleton() {
            return RefInvoker.getField(null, ClassName, Field_IActivityManagerSingleton);
        }

        public static Object getService() {
            return RefInvoker.invokeStaticMethod(ClassName, Method_getService);
        }

        public static void setIActivityManagerSingleton(Object activityManagerSingleton) {
            RefInvoker.setField(null, ClassName, Field_IActivityManagerSingleton, activityManagerSingleton);
        }

    }

}
