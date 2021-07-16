package com.github.hanlyjiang.lib.common.wrapper

import android.app.Activity
import android.app.Application
import android.os.Bundle

/**
 * 简化 ActivityLifecycleCallbacks 接口实现
 * @author hanlyjiang 2021/7/15 11:47 下午
 * @version 1.0
 */
open class ActivityLifecycleCallbacksWrapper : Application.ActivityLifecycleCallbacks {
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
    }
}