package cn.hanlyjiang.hjapf.hook.activity;

import android.app.Activity;
import android.app.Application;
import android.app.Instrumentation;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.github.hanlyjiang.lib.common.utils.LogUtil;
import com.github.hanlyjiang.lib.common.utils.RefInvoker;


/**
 * 直接 Hook Activity 的 Instrumentation 字段
 *
 * @author hanlyjiang 5/21/21 1:24 PM
 * @version 1.0
 */
public class ActivityInstrumentationHook {

    private static final String FIELD_INSTRUMENTATION1 = "mInstrumentation";
    private static final Class<Activity> CLASS_ACTIVITY = Activity.class;

    /**
     * 为指定的 Activity 安装 Hook
     *
     * @param activity 需要安装的Activity
     */
    public static void install(Activity activity) {
        hookActivityInstrumentation(activity);
    }

    /**
     * 安装到所有Activity
     *
     * @param app
     */
    public static void install(Application app) {
        app.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                install(activity);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {

            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {

            }
        });
    }

    private static void hookActivityInstrumentation(Activity activity) {
        Instrumentation rawInstrumentation = (Instrumentation) RefInvoker.getField(activity, CLASS_ACTIVITY, FIELD_INSTRUMENTATION1);
        RefInvoker.setField(activity, CLASS_ACTIVITY, FIELD_INSTRUMENTATION1,
                new HookInstrumentation(rawInstrumentation));
    }

    public static class HookInstrumentation extends Instrumentation {

        private final Instrumentation instrumentation;

        public HookInstrumentation(Instrumentation instrumentation) {
            this.instrumentation = instrumentation;
        }

        public ActivityResult execStartActivity(
                Context who, IBinder contextThread, IBinder token, Activity target,
                Intent intent, int requestCode, Bundle options) {
            LogUtil.d("HookInstrumentation invoked before target invoked!");
            ActivityResult activityResult = (ActivityResult) RefInvoker.invokeMethod(instrumentation, Instrumentation.class, "execStartActivity"
                    , new Class[]{Context.class, IBinder.class, IBinder.class, Activity.class, Intent.class, int.class, Bundle.class},
                    new Object[]{who, contextThread, token, target, intent, requestCode, options});
            LogUtil.d("HookInstrumentation invoked after target invoked!");
            return activityResult;
        }
    }
}
