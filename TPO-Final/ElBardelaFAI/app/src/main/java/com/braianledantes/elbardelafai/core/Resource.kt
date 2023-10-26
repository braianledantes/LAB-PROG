package com.braianledantes.elbardelafai.core

sealed class Resource<out T> {
    object Undefined : Resource<Nothing>()
    object Loading : Resource<Nothing>()
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val exception: Exception) : Resource<Nothing>()
}