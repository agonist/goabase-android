package com.onionsquare.goabase.domain.usecase

import com.onionsquare.goabase.Result
import com.onionsquare.goabase.domain.repository.GoabaseRepository
import com.onionsquare.goabase.model.Party
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PartiesUseCase(private val remoteRepository: GoabaseRepository) {

    fun listPartiesByCountry(country: String): Flow<State<List<Party>>> {
        return remoteRepository.getPartiesByCountry(country)
                .map {
                    when (it) {
                        is Result.Success -> {
                            State.Success(it.data.parties)
                        }
                        is Result.Error -> {
                            State.Error(it.type)
                        }
                    }
                }

    }

}