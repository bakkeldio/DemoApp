package com.example.demoapp.domain

import okhttp3.ResponseBody

sealed class ApiResult<out T> {
    data class Success<T>(val data : T): ApiResult<T>()
    data class Error(val data: String): ApiResult<Nothing>()
    data class NetworkError(val data : String):ApiResult<Nothing>()
    data class EmptyResult(val data: String): ApiResult<Nothing>()
    object Loading:ApiResult<Nothing>()
}