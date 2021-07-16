package com.github.hanlyjiang.lib.common.iface

import android.app.Activity

/**
 * On app status callback
 *
 * @constructor Create empty On app status callback
 */
interface OnAppStatusCallback {

    /**
     * On app to foreground
     *
     * @param currentActivity 当前页面
     */
    fun onAppToForeground(currentActivity: Activity)

    /**
     * On app to background
     *
     * @param currentActivity 当前页面
     */
    fun onAppToBackground(currentActivity: Activity)

}