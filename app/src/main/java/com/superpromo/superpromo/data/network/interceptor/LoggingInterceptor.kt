package com.superpromo.superpromo.data.network.interceptor

import android.annotation.SuppressLint
import android.util.Log
import okhttp3.Interceptor
import okhttp3.MediaType
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import java.io.IOException

class LoggingInterceptor : Interceptor {
    @SuppressLint("LogNotTimber")
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val t1 = System.nanoTime()
        Log.i(
            "OkHttp",
            String.format(
                "--> Sending request %s on %s%n%s",
                request.url,
                chain.connection(),
                request.headers
            )
        )
        val requestBuffer = Buffer()
        request.body?.writeTo(requestBuffer)
        Log.i("OkHttp", requestBuffer.readUtf8())
        val response: Response = chain.proceed(request)
        val t2 = System.nanoTime()
        Log.i(
            "OkHttp",
            String.format(
                "<-- Received response for %s in %.1fms%n%s",
                response.request.url,
                (t2 - t1) / 1e6,
                response.headers
            )
        )
        val contentType: MediaType? = response.body?.contentType()
        val content: String = response.body?.string() ?: ""
        Log.d("OkHttp", content)
        val wrappedBody: ResponseBody = content.toResponseBody(contentType)
        return response.newBuilder().body(wrappedBody).build()
    }
}
