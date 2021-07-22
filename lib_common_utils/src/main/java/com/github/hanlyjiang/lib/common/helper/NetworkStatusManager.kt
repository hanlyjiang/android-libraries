package com.github.hanlyjiang.lib.common.helper.network

import android.content.Context
import android.os.Build


/**
 * 网络状态监听管理对象
 * @author hanlyjiang 2021/7/21 10:41 下午
 * @version 1.0
 */
object NetworkStatusManager : BaseNetworkStatusHelper() {

    private lateinit var networkStatsHelper: INetworkStatusHelper

    private var initialed: Boolean = false

    private var hasUnRegisterListeners = false

    /**
     * Init
     *
     * @param appContext Context
     */
    override fun init(appContext: Context) {
        networkStatsHelper = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            NetworkStatusHelperV21()
        } else {
            NetworkStatusHelperV1()
        }
        initialed = true
        if (hasUnRegisterListeners) {
            listeners.forEach {
                networkStatsHelper.registerNetworkStatusChangeListener(listener = it)
            }
            listeners.clear()
            hasUnRegisterListeners = false
        }
    }

    override fun registerNetworkStatusChangeListener(listener: INetworkStatusChangeListener) {
        if (initialed) {
            networkStatsHelper.registerNetworkStatusChangeListener(listener)
        } else {
            super.registerNetworkStatusChangeListener(listener)
            hasUnRegisterListeners = true
        }
    }

    override fun unRegisterNetworkStatusChangeListener(listener: INetworkStatusChangeListener) {
        if (initialed) {
            networkStatsHelper.unRegisterNetworkStatusChangeListener(listener)
        } else {
            super.unRegisterNetworkStatusChangeListener(listener)
        }
    }

    override fun destroy(context: Context) {
        networkStatsHelper.destroy(context)
        initialed = false
    }

}
