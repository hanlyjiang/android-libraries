package com.github.hanlyjiang.lib.common.helper

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Process
import com.github.hanlyjiang.lib.common.wrapper.ActivityLifecycleCallbacksWrapper
import java.lang.ref.WeakReference
import java.util.*
import kotlin.collections.ArrayDeque

/**
 * APP 杀手，彻底退出自己的APP
 * @author hanlyjiang 2021/7/22 10:50 下午
 * @version 1.0
 */
object AppKiller : ActivityLifecycleCallbacksWrapper() {

    private val activityStack = ArrayDeque<WeakReference<Activity?>>()
    private lateinit var app: Application

    /**
     * Init
     *
     * @param app Application
     */
    fun init(app: Application) {
        this.app = app
        app.registerActivityLifecycleCallbacks(this)
    }

    /**
     * Destroy
     *
     */
    fun destroy() {
        app.unregisterActivityLifecycleCallbacks(this)
    }

    /**
     * Kill app
     */
    fun killApp() {
        // 杀掉所有Activity
        while (activityStack.isNotEmpty()) {
            val item = activityStack.removeLast()
            item.get()?.run {
                this.finish()
            }
        }
        (app.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager).run {
            // 杀后台进程
            killBackgroundProcesses(app.packageName)
        }
        // 杀掉自己这个进程
        Process.killProcess(Process.myPid())
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        super.onActivityCreated(activity, savedInstanceState)
        if (!containsActivity(activity)) {
            activityStack.addLast(WeakReference(activity))
        }
    }

    override fun onActivityDestroyed(activity: Activity) {
        super.onActivityDestroyed(activity)
        if (containsActivity(activity)) {
            activityStack.removeLast()
        }
    }

    private fun containsActivity(activity: Activity): Boolean {
        var contains = false
        activityStack.forEach {
            if (it.get() != null && activity == it.get()) {
                contains = true
                return@forEach
            }
        }
        return contains
    }

}