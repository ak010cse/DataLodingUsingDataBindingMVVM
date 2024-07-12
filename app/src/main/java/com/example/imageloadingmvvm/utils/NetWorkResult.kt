package com.example.imageloadingmvvm.utils


sealed class NetWorkResult <out T> (val status: ApiStatus, val data:T?, val message:String){
    data class Success<out T>(val response:T?):NetWorkResult<T>(status = ApiStatus.SUCCESS, data = response, message = "")
    data class Error<out T>(val error: T?, val exception: String): NetWorkResult<T>(status = ApiStatus.ERROR, data = error, message = exception)
    data class Loading<out T>(val isLoading:Boolean): NetWorkResult<T>(status = ApiStatus.LOADING, data = null, message = "")
}

enum class ApiStatus {
    SUCCESS,
    ERROR,
    LOADING
}