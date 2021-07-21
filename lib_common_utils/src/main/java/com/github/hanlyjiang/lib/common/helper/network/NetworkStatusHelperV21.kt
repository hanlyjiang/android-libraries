package com.github.hanlyjiang.lib.common.helper.network

import android.content.Context
import android.net.*
import android.os.Build

/**
 *
 * @author hanlyjiang 2021/7/21 11:15 下午
 * @version 1.0
 */
class NetworkStatusHelperV21 : BaseNetworkStatusHelper() {

    override fun init(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(),
            callback
        )
    }

    override fun destroy(context: Context) {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        connectivityManager.unregisterNetworkCallback(callback)
    }

    private var callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            listeners.forEach {
                if (it is INetworkStatusChangeListener2) {
                    it.onAvailable(network)
                }
            }
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            super.onLosing(network, maxMsToLive)
            listeners.forEach {
                if (it is INetworkStatusChangeListener2) {
                    it.onLosing(network, maxMsToLive)
                }
            }
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            listeners.forEach {
                if (it is INetworkStatusChangeListener2) {
                    it.onLost(network)
                }
            }
        }

        override fun onUnavailable() {
            super.onUnavailable()
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                listeners.forEach {
                    if (it is INetworkStatusChangeListener2) {
                        it.onUnavailable()
                    }
                }
            }
        }

        override fun onCapabilitiesChanged(
            network: Network,
            networkCapabilities: NetworkCapabilities
        ) {
            super.onCapabilitiesChanged(network, networkCapabilities)
            listeners.forEach {
                if (it is INetworkStatusChangeListener2) {
                    it.onCapabilitiesChanged(network, networkCapabilities)
                }
            }
        }

        override fun onLinkPropertiesChanged(network: Network, linkProperties: LinkProperties) {
            super.onLinkPropertiesChanged(network, linkProperties)
            listeners.forEach {
                if (it is INetworkStatusChangeListener2) {
                    it.onLinkPropertiesChanged(network, linkProperties)
                }
            }
        }

        override fun onBlockedStatusChanged(network: Network, blocked: Boolean) {
            super.onBlockedStatusChanged(network, blocked)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                listeners.forEach {
                    if (it is INetworkStatusChangeListener2) {
                        it.onBlockedStatusChanged(network, blocked)
                    }
                }
            }
        }
    }


}