package com.github.hanlyjiang.lib.common.helper.network

import android.content.Context

/**
 * 网络状态帮助器的接口定义
 *
 * @author hanlyjiang 2021/7/21 11:09 下午
 * @version 1.0
 */
interface INetworkStatusHelper {

    fun init(context: Context)

    fun destroy(context: Context)

    fun registerNetworkStatusChangeListener(listener: INetworkStatusChangeListener)

    fun unRegisterNetworkStatusChangeListener(listener: INetworkStatusChangeListener)
}