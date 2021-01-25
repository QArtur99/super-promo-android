package com.superpromo.superpromo.repository

import com.superpromo.superpromo.data.exception.NetworkException
import com.superpromo.superpromo.di.IoDispatcher
import com.superpromo.superpromo.repository.state.ResultStatus
import com.superpromo.superpromo.repository.util.ApiCodes
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

open class BaseRepository(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    protected suspend fun <T> safeApiCall(
        call: suspend () -> T,
        errorMessage: String
    ): ResultStatus<T> = withContext(ioDispatcher) {
        try {
            val response = call.invoke()
            ResultStatus.Success(response)
        } catch (e: Exception) {
            when (e) {
                is NetworkException -> ResultStatus.Error(ApiCodes.NO_CONNECTION, e.toString())
                else -> ResultStatus.Error(ApiCodes.UNKNOWN, e.toString())
            }
        }
    }
}