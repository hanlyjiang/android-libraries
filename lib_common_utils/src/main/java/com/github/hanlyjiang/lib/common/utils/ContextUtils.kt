package com.github.hanlyjiang.lib.common.utils

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.Context

/**
 * Context 相关工具类
 * @author hanlyjiang 2021/7/17 2:16 下午
 * @version 1.0
 */
object ContextUtils {

    /**
     * 从 context 中获取ApplicationContext
     *
     * @param context Context 对象
     * @return ApplicationContext
     */
    fun getApplicationContext(context: Context): Context? {
        return context as? Application
            ?: when (context) {
                is Activity -> {
                    context.application
                }
                is Service -> {
                    context.application
                }
                else -> {
                    context.applicationContext
                }
            }
    }

    /**
     * 判断是否 application context，注意：仅一般情况下有效，对于自己构建的Context不一定判断准确
     *
     * @param context Context
     * @return true - Application 的 Context， false - Activity 或 Service 的 Context
     */
    fun isApplicationContext(context: Context): Boolean {
        return !(context is Activity || context is Service)
    }

}