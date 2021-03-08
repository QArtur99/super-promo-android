package com.superpromo.superpromo.repository

import com.superpromo.superpromo.data.exception.NetworkException
import com.superpromo.superpromo.di.IoDispatcher
import com.superpromo.superpromo.repository.state.ResultApi
import com.superpromo.superpromo.repository.util.ApiCodes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

open class BaseRepository(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    protected suspend fun <T> safeApiCall(
        call: suspend () -> T,
        errorMessage: String
    ): ResultApi<T> = withContext(ioDispatcher) {
        try {
            val response = call.invoke()
            ResultApi.Success(response)
        } catch (e: Exception) {
            when (e) {
                is NetworkException -> ResultApi.Error(ApiCodes.NO_CONNECTION, e.toString())
                else -> ResultApi.Error(ApiCodes.UNKNOWN, e.toString())
            }
        }
    }
}
