package com.github.hanlyjiang.lib.common.helper.network;

import android.net.Network;

import androidx.annotation.Nullable;

/**
 * 网络状态变化监听程序
 * @author hanlyjiang 2021/7/21 10:42 下午
 * @version 1.0
 */
public interface INetworkStatusChangeListener {

    /**
     * 网络变得可用
     *
     * @param network API 21以下版本为null，以上版本可用
     */
    void onAvailable(@Nullable Network network);

    /**
     * 网络变得不可用
     *
     * @param network API 21以下版本为null，以上版本可用
     */
    void onLost(@Nullable Network network);
}
