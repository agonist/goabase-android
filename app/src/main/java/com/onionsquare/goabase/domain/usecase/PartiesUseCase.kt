package com.onionsquare.goabase.domain.usecase

import com.onionsquare.goabase.Result
import com.onionsquare.goabase.domain.repository.GoabaseRepository
import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PartiesUseCase(private val remoteRepository: GoabaseRepository) {

    fun listPartiesByCountry(country: String): Flow<Resource<List<Party>>> {
        return remoteRepository.getPartiesByCountry(country)
                .map {
                    when (it) {
                        is Resource.Success -> {
                            Resource.Success(it.data.parties)
                        }
                        is Resource.Error -> {
                            Resource.Error(it.errorData)
                        }
                    }
                }

    }

}