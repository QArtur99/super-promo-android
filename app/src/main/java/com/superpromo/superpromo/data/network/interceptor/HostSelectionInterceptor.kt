package com.superpromo.superpromo.data.network.interceptor

import com.superpromo.superpromo.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HostSelectionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val newUrl = request.url.newBuilder()
            .host(BuildConfig.BASE_URL)
            .build()

        return chain.proceed(newUrl)
    }
}
