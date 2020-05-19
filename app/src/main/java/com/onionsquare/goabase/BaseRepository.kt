package com.onionsquare.goabase

import retrofit2.Response

abstract class BaseRepository {

    protected suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): Result<T> {
        val response: Response<T>
        try {
            response = call.invoke()
        } catch (t: Throwable) {
            return Result.Error(GoabaseException(t))
        }

        if (!response.isSuccessful) {
            return Result.Error(GoabaseException(Throwable("Unknow error")))
        }
        return Result.Success(response.body()!!)
    }
}

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val exception: Exception) : Result<Nothing>()
}

class GoabaseException(throwable: Throwable): Exception(throwable)