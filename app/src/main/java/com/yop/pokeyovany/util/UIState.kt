package com.yop.pokeyovany.util

sealed class UIState <out T> {

    object Loading : UIState<Nothing>()

    data class Success<T>(val response: T) : UIState<T>()

    data class Error(val error: Exception) : UIState<Nothing>()
}