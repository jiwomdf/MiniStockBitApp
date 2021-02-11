package com.katilijiwo.ministockbitapp.util

sealed class Resource<out T: Any> {
    data class Success<out T: Any>(val data: T) : Resource<T>()
    data class Error(val error: Exception) : Resource<Nothing>()
}