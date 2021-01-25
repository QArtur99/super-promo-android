package com.superpromo.superpromo.di

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.superpromo.superpromo.BuildConfig
import com.superpromo.superpromo.data.network.SuperPromoApi
import com.superpromo.superpromo.data.network.interceptor.ConnectionInterceptor
import com.superpromo.superpromo.data.network.interceptor.HostSelectionInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @Singleton
    fun provideHostSelectionInterceptor() = HostSelectionInterceptor()

    @Provides
    @Singleton
    fun provideConnectionInterceptor(context: Context) = ConnectionInterceptor(context)

    @Provides
    @Singleton
    fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        connectionInterceptor: ConnectionInterceptor,
        hostSelectionInterceptor: HostSelectionInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .addInterceptor(connectionInterceptor)
        .addInterceptor(hostSelectionInterceptor)
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient, moshi: Moshi): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit): SuperPromoApi = retrofit.create(
        SuperPromoApi::class.java
    )
}
