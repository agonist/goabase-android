package com.onionsquare.goabase

import retrofit2.Response

abstract class BaseRepository {

    protected suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): Result<T> {
        val response: Response<T>
        try {
            response = call.invoke()
        } catch (t: Throwable) {
            return Result.Error(ErrorType.UNKNOWN)
        }

        if (!response.isSuccessful) {
            return Result.Error(ErrorType.UNKNOWN)
        }
        return Result.Success(response.body()!!)
    }


    enum class ErrorType {
        UNKNOWN
    }
}

class GoabaseException(throwable: Throwable): Exception(throwable)

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class Error(val type: BaseRepository.ErrorType) : Result<Nothing>()
}