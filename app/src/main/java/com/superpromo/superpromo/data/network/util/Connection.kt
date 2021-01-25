package com.superpromo.superpromo.data.network.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

object Connection {

    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
            ?: return false
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkConnectionApiAboveApi23(cm)
        } else {
            checkConnectionApiBelow23(cm)
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private fun checkConnectionApiAboveApi23(cm: ConnectivityManager): Boolean {
        val network = cm.activeNetwork
        val nc = cm.getNetworkCapabilities(network)
        if (nc != null) {
            when {
                nc.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                nc.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
                nc.hasTransport(NetworkCapabilities.TRANSPORT_VPN) -> return true
                nc.hasTransport(NetworkCapabilities.TRANSPORT_WIFI_AWARE) -> return true
                nc.hasTransport(NetworkCapabilities.TRANSPORT_LOWPAN) -> return true
            }
        }
        return false
    }

    @SuppressWarnings("deprecation")
    private fun checkConnectionApiBelow23(cm: ConnectivityManager): Boolean {
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}