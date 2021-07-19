package com.github.hanlyjiang.lib.common.helper

import android.app.Activity
import android.content.ComponentCallbacks2
import android.content.Context
import android.content.res.Configuration
import com.github.hanlyjiang.lib.common.iface.OnAppStatusCallback
import com.github.hanlyjiang.lib.common.utils.ContextUtils
import com.github.hanlyjiang.lib.common.utils.LogUtil
import com.github.hanlyjiang.lib.common.wrapper.ActivityLifecycleCallbacksWrapper

/**
 * App 状态帮助器
 * @author hanlyjiang 2021/7/15 11:46 下午
 * @version 1.0
 */
object AppStatusHelper : ActivityLifecycleCallbacksWrapper(), ComponentCallbacks2 {

    private var mActivityNum = 0

    private val mStatusCallbacks = mutableListOf<OnAppStatusCallback>()

    private var initialed = false

    /**
     * Init ，必须初始化才有作用
     *
     * @param context Context
     */
    fun init(context: Context): AppStatusHelper {
        ContextUtils.getApplication(context)?.run {
            registerActivityLifecycleCallbacks(this@AppStatusHelper)
            registerComponentCallbacks(this@AppStatusHelper)
            initialed = true
        }
        return this
    }

    /**
     * Registry app status callback，注意：需要自行管理注册及注销，以避免内存泄漏
     *
     * @param callback 回调
     */
    fun registryAppStatusCallback(callback: OnAppStatusCallback) {
        if (!initialed) {
            LogUtil.w("尚未初始化，请不要忘记初始化")
        }
        if (!mStatusCallbacks.contains(callback)) {
            mStatusCallbacks.add(callback)
        }
    }

    /**
     * Un registry app status callback
     *
     * @param callback 回调
     */
    fun unRegistryAppStatusCallback(callback: OnAppStatusCallback) {
        if (!initialed) {
            LogUtil.w("尚未初始化，请不要忘记初始化")
        }
        if (mStatusCallbacks.contains(callback)) {
            mStatusCallbacks.remove(callback)
        }
    }

    override fun onActivityStarted(activity: Activity) {
        super.onActivityStarted(activity)
        mActivityNum++
        if (mActivityNum == 1) {
            onAppForeground(activity)
        }
    }

    override fun onActivityStopped(activity: Activity) {
        super.onActivityStopped(activity)
        mActivityNum--
        if (mActivityNum <= 0) {
            mActivityNum = 0
            onAppBackground(activity)
        }
    }

    private fun onAppForeground(activity: Activity) {
        mStatusCallbacks.forEach{
            it.onAppToForeground(activity)
        }
    }

    private fun onAppBackground(activity: Activity) {
        mStatusCallbacks.forEach{
            it.onAppToBackground(activity)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        mStatusCallbacks.forEach {
            it.onConfigurationChanged(newConfig)
        }
    }

    override fun onLowMemory() {
        mStatusCallbacks.forEach {
            it.onLowMemory()
        }
    }

    override fun onTrimMemory(level: Int) {
        mStatusCallbacks.forEach {
            it.onTrimMemory(level)
        }
    }
}