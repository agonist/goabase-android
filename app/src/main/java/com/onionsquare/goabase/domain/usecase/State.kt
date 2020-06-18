package com.onionsquare.goabase.domain.usecase

import com.onionsquare.goabase.BaseRepository


sealed class State<out T : Any> {
    object Init : State<Nothing>()
    object Loading : State<Nothing>()
    data class Success<out T : Any>(val data: T) : State<T>()
    data class Error(val error: BaseRepository.ErrorType) : State<Nothing>()
}
