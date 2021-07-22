package com.github.hanlyjiang.lib.common.helper.network

import android.content.Context

/**
 *  5.1 以下版本的实现，基于广播接收器
 * @author hanlyjiang 2021/7/21 11:08 下午
 * @version 1.0
 */
class NetworkStatusHelperV1 : BaseNetworkStatusHelper() {

    private var receiver: NetworkChangeReceiver? = null

    private var listener = object : OnNetworkChangedListener {
        override fun onNetworkChanged(hasNetwork: Boolean) {
            for (listener in listeners) {
                if (hasNetwork) {
                    listener.onAvailable(null)
                } else {
                    listener.onLost(null)
                }
            }
        }
    }

    override fun init(context: Context) {
        NetworkChangeReceiver.registerNetworkChangeListener(listener)
        receiver = NetworkChangeReceiver.register(context)
    }

    override fun destroy(context: Context) {
        receiver?.run {
            context.unregisterReceiver(receiver)
            receiver = null
        }
        NetworkChangeReceiver.unRegisterNetworkChangeListener(listener)
    }
}