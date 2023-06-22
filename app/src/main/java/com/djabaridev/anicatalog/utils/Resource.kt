package com.djabaridev.anicatalog.utils

sealed class Resource<T: Any> {
    class Success<T: Any>(val data: T): Resource<T>()
    class Error<T: Any>(val code: Int, val message: String? = null): Resource<T>()
    class Exception<T: Any>(val exception: Throwable): Resource<T>()
}