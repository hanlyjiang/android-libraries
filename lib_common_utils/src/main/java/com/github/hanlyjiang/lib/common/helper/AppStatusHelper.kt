package com.github.hanlyjiang.lib.common.helper

import android.app.Activity
import android.os.Build
import com.github.hanlyjiang.lib.common.iface.OnAppStatusCallback
import com.github.hanlyjiang.lib.common.utils.AppInfoUtils
import com.github.hanlyjiang.lib.common.wrapper.ActivityLifecycleCallbacksWrapper

/**
 * App 状态帮助器
 * @author hanlyjiang 2021/7/15 11:46 下午
 * @version 1.0
 */
object AppStatusHelper : ActivityLifecycleCallbacksWrapper() {

    private var mActivityNum = 0

    private val mStatusCallback = mutableListOf<OnAppStatusCallback>()

    /**
     * Registry app status callback，注意：需要自行管理注册及注销，以避免内存泄漏
     *
     * @param callback 回调
     */
    fun registryAppStatusCallback(callback: OnAppStatusCallback) {
        if (!mStatusCallback.contains(callback)) {
            mStatusCallback.add(callback)
        }
    }

    /**
     * Un registry app status callback
     *
     * @param callback 回调
     */
    fun unRegistryAppStatusCallback(callback: OnAppStatusCallback) {
        if (mStatusCallback.contains(callback)) {
            mStatusCallback.remove(callback)
        }
    }

    override fun onActivityStarted(activity: Activity) {
        super.onActivityStarted(activity)
        mActivityNum++
        if (mActivityNum == 1) {
            onAppForeground(activity)
        }
    }

    override fun onActivityPaused(activity: Activity) {
        super.onActivityPaused(activity)
        mActivityNum--
        if (mActivityNum <= 0) {
            mActivityNum = 0
            onAppBackground(activity)
        }
    }

    private fun onAppForeground(activity: Activity) {
        var confirm = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (AppInfoUtils.getAppTasksActivityCount(activity.applicationContext) == 1) {
                confirm = true
            }
        }
        if (!confirm) {
            return
        }
        for (callback in mStatusCallback) {
            callback.onAppToForeground(activity)
        }
    }

    private fun onAppBackground(activity: Activity) {
        var confirm = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (AppInfoUtils.getAppTasksActivityCount(activity.applicationContext) == 0) {
                confirm = true
            }
        }
        if (!confirm) {
            return
        }
        for (callback in mStatusCallback) {
            callback.onAppToBackground(activity)
        }
    }
}