package com.besirkaraoglu.rickandmorty.util

sealed class DataState<out T> {
    object Loading: DataState<Nothing>()
    data class Success<out T>(val data:T): DataState<T>()
    data class Failure(val e: Exception): DataState<Nothing>()
}