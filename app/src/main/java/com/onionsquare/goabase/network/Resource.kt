package com.onionsquare.goabase.network


sealed class Resource<T> {

    data class Success<T>(
            val data: T
    ) : Resource<T>()

    data class Error<T>(
            val errorData: String
    ) : Resource<T>()
}

