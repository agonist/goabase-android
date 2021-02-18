package com.onionsquare.goabase

import com.onionsquare.goabase.network.Resource
import retrofit2.Response

abstract class BaseRepository {

    protected suspend fun <T : Any> apiCall(call: suspend () -> Response<T>): Resource<T> {
        val response: Response<T>
        try {
            response = call.invoke()
        } catch (t: Throwable) {
            return Resource.Error(ErrorType.UNKNOWN.toString())
        }

        if (!response.isSuccessful) {
            return Resource.Error(ErrorType.UNKNOWN.toString())
        }
        return Resource.Success(response.body()!!)
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