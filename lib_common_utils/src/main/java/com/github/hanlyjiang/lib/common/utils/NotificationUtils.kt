package com.github.hanlyjiang.lib.common.utils

import android.app.NotificationChannel
import android.content.Context
import android.os.RemoteException
import androidx.core.app.NotificationChannelCompat
import androidx.core.app.NotificationManagerCompat

/**
 * Android 通知相关工具类
 * @author hanlyjiang 2021/7/17 11:44 下午
 * @version 1.0
 */
object NotificationUtils {

    /**
     * Create notification channel
     *
     * @param context  Context
     * @param channel NotificationChannel
     * @return true - 创建调用成功； false - 创建调用失败
     */
    fun createNotificationChannel(
        context: Context,
        channel: NotificationChannelCompat
    ): Boolean {
        return try {
            NotificationManagerCompat.from(context).createNotificationChannel(channel)
            true
        } catch (e: RemoteException) {
            false
        }
    }

    /**
     * Create notification channel
     *
     * @param context 上下文
     * @param channelId id
     * @param importance 重要程度
     * @param name 名称
     * @param description 描述
     * @return true - 创建调用成功； false - 创建调用失败
     */
    fun createNotificationChannel(
        context: Context,
        channelId: String,
        importance: Int,
        name: String,
        description: String,
    ): Boolean {
        val channel = NotificationChannelCompat
            .Builder(channelId, importance)
            .setName(name)
            .setDescription(description)
            .build()
        return try {
            NotificationManagerCompat.from(context).createNotificationChannel(channel)
            true
        } catch (e: RemoteException) {
            false
        }
    }

}