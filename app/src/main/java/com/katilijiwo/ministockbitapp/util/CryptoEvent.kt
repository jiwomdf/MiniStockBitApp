package com.katilijiwo.ministockbitapp.util

sealed class CryptoEvent<out T> {
    class Success<out T>(val data: T): CryptoEvent<T>()
    class NotFound<out T>(val message: String = "Data not found") : CryptoEvent<T>()
    class Error(val message: String): CryptoEvent<Nothing>()
    object Loading: CryptoEvent<Nothing>()
}