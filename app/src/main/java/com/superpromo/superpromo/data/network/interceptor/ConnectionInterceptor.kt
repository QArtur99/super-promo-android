package com.superpromo.superpromo.data.network.interceptor

import android.content.Context
import com.superpromo.superpromo.BuildConfig
import com.superpromo.superpromo.data.exception.NetworkException
import com.superpromo.superpromo.data.network.util.Connection
import okhttp3.Interceptor
import okhttp3.Response

class ConnectionInterceptor(
    private val context: Context
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if(!Connection.isConnected(context)){
         throw NetworkException()
        }
        return chain.proceed(chain.request())
    }
}
