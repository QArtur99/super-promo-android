package com.superpromo.superpromo.data.network.interceptor

import com.superpromo.superpromo.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class HostSelectionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        chain.connection()

        val newUrl = request.url.newBuilder()
            .host(BuildConfig.HOST_URL)
            .port(BuildConfig.HOST_PORT)
            .build()
        request = request.newBuilder().url(newUrl).build()
        return chain.proceed(request)
    }
}
