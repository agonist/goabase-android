package com.onionsquare.goabase.network

import com.onionsquare.goabase.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import retrofit2.Call
import retrofit2.CallAdapter
import retrofit2.awaitResponse
import java.lang.reflect.Type

/**
 * To convert retrofit response to Flow<Resource<T>>.
 * Inspired from FlowCallAdapterFactory
 */
class FlowResourceCallAdapter<R>(
    private val responseType: Type,
    private val isSelfExceptionHandling: Boolean
) : CallAdapter<R, Flow<Resource<R>>> {

    override fun responseType() = responseType

    @ExperimentalCoroutinesApi
    override fun adapt(call: Call<R>) = flow<Resource<R>> {

        val resp = call.awaitResponse()

        if (resp.isSuccessful) {
            resp.body()?.let { data ->
                // Success
                emit(Resource.Success(data))
            } ?: kotlin.run {
                // Error
                emit(Resource.Error("Response can't be null"))
            }
        } else {
            // Error
            val errorBody = resp.message()
            emit(Resource.Error(errorBody))
        }

    }.catch { error: Throwable ->
        if (isSelfExceptionHandling) {
            emit(Resource.Error(error.message ?: "Something went wrong"))
        } else {
            throw error
        }
    }
}