package com.superpromo.superpromo.repository.state

sealed class ResultStatus<out R> {

    object Loading : ResultStatus<Nothing>()
    data class Success<out T>(val data: T) : ResultStatus<T>()
    data class Error(val code: Int, val message: String) : ResultStatus<Nothing>()

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
