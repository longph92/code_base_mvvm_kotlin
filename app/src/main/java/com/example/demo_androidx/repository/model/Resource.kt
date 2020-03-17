package com.example.demo_androidx.repository.model

sealed class Resource<T>(
    val data: T? = null,
    val errorCode: Int? = null
) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class HideLoading<T>(data: T? = null) : Resource<T>(data)
    class DataError<T>(errorCode: Int) : Resource<T>(null, errorCode)
}