package com.github.hanlyjiang.lib.common.helper.network

/**
 * NetworkStatusHelper 基础实现
 *
 * @author hanlyjiang 2021/7/21 11:16 下午
 * @version 1.0
 */
abstract class BaseNetworkStatusHelper : INetworkStatusHelper {

    protected val listeners = mutableListOf<INetworkStatusChangeListener>()

    override fun registerNetworkStatusChangeListener(listener: INetworkStatusChangeListener) {
        listeners.add(listener)
    }

    override fun unRegisterNetworkStatusChangeListener(listener: INetworkStatusChangeListener) {
        listeners.remove(listener)
    }
}