package com.github.hanlyjiang.lib.common.helper.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.os.Parcelable
import android.util.Log
import java.util.*

interface OnNetworkChangedListener {
    fun onNetworkChanged(hasNetwork: Boolean)
}

/**
 * 网络状态监听广播接收器
 *
 * @author hanlyjiang 2021/7/21 10:51 下午
 * @version 1.0
 */
class NetworkChangeReceiver : BroadcastReceiver() {

    companion object {
        private const val TAG = "NetworkChangeReceiver"

        /**
         * 动态 - 注册一个网络监听广播接收器
         * <br></br> 如果需要静态注册：
         * <pre>`
         * <uses-permission android:name="android.permission.INTERNET"/>
         * <!--允许读取网络状态 -->
         * <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"></uses-permission>
         * <!--允许读取wifi网络状态-->
         * <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
        `</pre> *
         *
         * @param context
         * @return NetworkChangeReceiver
         */
        fun register(context: Context): NetworkChangeReceiver {
            val filter = IntentFilter()
            filter.addAction("android.net.conn.CONNECTIVITY_CHANGE")
            filter.addAction("android.net.wifi.WIFI_STATE_CHANGED")
            filter.addAction("android.net.wifi.STATE_CHANGE")
            val networkChangeReceiver = NetworkChangeReceiver()
            context.registerReceiver(networkChangeReceiver, filter)
            return networkChangeReceiver
        }

        /**
         * Register network change listener
         *
         * @param listener OnNetworkChangedListener
         */
        fun registerNetworkChangeListener(listener: OnNetworkChangedListener) {
            NetworkStateMemHolder.instance.addOnNetworkChangedListener(listener)
        }

        /**
         * Unregister network change listener
         *
         * @param listener OnNetworkChangedListener
         */
        fun unRegisterNetworkChangeListener(listener: OnNetworkChangedListener) {
            NetworkStateMemHolder.instance.removeOnNetworkChangeListener(listener)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        // 这个监听wifi的打开与关闭，与wifi的连接无关
        if (WifiManager.WIFI_STATE_CHANGED_ACTION == intent.action) {
            when (intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE, 0)) {
                WifiManager.WIFI_STATE_DISABLED -> NetworkStateMemHolder.instance
                    .setEnableWifi(false)
                WifiManager.WIFI_STATE_DISABLING -> {
                }
                WifiManager.WIFI_STATE_ENABLING -> {
                }
                WifiManager.WIFI_STATE_ENABLED -> NetworkStateMemHolder.instance
                    .setEnableWifi(true)
                WifiManager.WIFI_STATE_UNKNOWN -> {
                }
                else -> {
                }
            }
        }
        // 这个监听wifi的连接状态即是否连上了一个有效无线路由，当上边广播的状态是WifiManager
        // .WIFI_STATE_DISABLING，和WIFI_STATE_DISABLED的时候，根本不会接到这个广播。
        // 在上边广播接到广播是WifiManager.WIFI_STATE_ENABLED状态的同时也会接到这个广播，
        // 当然刚打开wifi肯定还没有连接到有效的无线
        if (WifiManager.NETWORK_STATE_CHANGED_ACTION == intent.action) {
            val parcelableExtra = intent
                .getParcelableExtra<Parcelable>(WifiManager.EXTRA_NETWORK_INFO)
            if (null != parcelableExtra) {
                val networkInfo = parcelableExtra as NetworkInfo
                val state = networkInfo.state
                val isConnected = state == NetworkInfo.State.CONNECTED
                Log.v(
                    TAG,
                    "isConnected$isConnected"
                )
                if (isConnected) {
                    NetworkStateMemHolder.instance.setWifi(true)
                } else {
                    NetworkStateMemHolder.instance.setWifi(false)
                }
            }
        }
        // 这个监听网络连接的设置，包括wifi和移动数据的打开和关闭。.
        // 最好用的还是这个监听。wifi如果打开，关闭，以及连接上可用的连接都会接到监听。见log
        // 这个广播的最大弊端是比上边两个广播的反应要慢，如果只是要监听wifi，我觉得还是用上边两个配合比较合适
        if (ConnectivityManager.CONNECTIVITY_ACTION == intent.action) {
            val manager = context
                .getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork = manager.activeNetworkInfo
            /* connected to the internet */
            if (activeNetwork == null) {
                Log.w(
                    TAG, "当前没有网络连接，请确保你已经打开网络 "
                )
                NetworkStateMemHolder.instance.setWifi(false)
                NetworkStateMemHolder.instance.setMobile(false)
                NetworkStateMemHolder.instance.setConnected(false)
                return
            }

            if (activeNetwork.isConnected) {
                when (activeNetwork.type) {
                    ConnectivityManager.TYPE_WIFI -> {
                        // connected to wifi
                        NetworkStateMemHolder.instance.setWifi(true)
                        Log.v(
                            TAG,
                            "当前WiFi连接可用 "
                        )
                    }
                    ConnectivityManager.TYPE_MOBILE -> {
                        // connected to the mobile provider's data plan
                        NetworkStateMemHolder.instance.setMobile(true)
                        Log.v(
                            TAG,
                            "当前移动网络连接可用 "
                        )
                    }
                    else -> {
                        NetworkStateMemHolder.instance.setConnected(true)
                    }
                }
            } else {
                NetworkStateMemHolder.instance.setConnected(false)
                Log.w(
                    TAG,
                    "当前没有网络连接，请确保你已经打开网络 "
                )
            }
        }
    }

}

internal class NetworkStateMemHolder internal constructor() {
    private val networkChangeListeners: MutableList<OnNetworkChangedListener> = ArrayList()

    fun setConnected(connected: Boolean) {
        this.connected = connected
        for (listener in networkChangeListeners) {
            listener.onNetworkChanged(connected)
        }
    }

    private var enableWifi = false
    private var wifi = false
    private var mobile = false
    private var connected = false
    fun setEnableWifi(enableWifi: Boolean) {
        this.enableWifi = enableWifi
    }

    fun setWifi(wifi: Boolean) {
        this.wifi = wifi
        if (wifi) {
            setConnected(true)
        }
    }

    fun setMobile(mobile: Boolean) {
        this.mobile = mobile
        if (mobile) {
            setConnected(true)
        }
    }

    fun addOnNetworkChangedListener(listener: OnNetworkChangedListener) {
        if (!networkChangeListeners.contains(listener)) {
            networkChangeListeners.add(listener)
        }
    }

    fun removeOnNetworkChangeListener(listener: OnNetworkChangedListener) {
        if (networkChangeListeners.contains(listener)) {
            networkChangeListeners.remove(listener)
        }
    }


    companion object {
        val instance = NetworkStateMemHolder()

        fun addNetworkChangeListener(listener: OnNetworkChangedListener) {
            instance.addOnNetworkChangedListener(listener)
        }
    }
}