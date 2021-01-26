package com.superpromo.superpromo.repository.state

sealed class ResultApi<out T> {

    object Loading : ResultApi<Nothing>()
    data class Success<out T>(val data: T) : ResultApi<T>()
    data class Error(val code: Int, val message: String) : ResultApi<Nothing>()

    val succeeded: Boolean
        get() = this is Success<*> && data != null

    override fun toString(): String {
        return when (this) {
            is Loading -> "Loading"
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error[code=$code message=$message]"
        }
    }
}
