package com.synrgy7team4.common

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
}

//
//sealed class Resource<out R> {
//    data class Success<out T>(val data: T) : Resource<T>()
//    data class Error(val errorMessage: String) : Resource<Nothing>()
//    data object Empty : Resource<Nothing>()
//}