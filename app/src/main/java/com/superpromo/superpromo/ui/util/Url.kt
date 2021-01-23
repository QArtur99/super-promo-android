package com.superpromo.superpromo.ui.util

import com.superpromo.superpromo.BuildConfig

object Url {
    fun getBaseUrl() = "http://" + BuildConfig.HOST_URL + ":" + BuildConfig.HOST_PORT
}