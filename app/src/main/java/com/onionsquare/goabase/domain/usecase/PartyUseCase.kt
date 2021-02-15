package com.onionsquare.goabase.domain.usecase

import com.onionsquare.goabase.domain.repository.GoabaseRepository
import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PartyUseCase(private val remoteRepository: GoabaseRepository) {

    fun getPartyDetailsById(id: String): Flow<Resource<Party>> {
        return remoteRepository.getPartyDetailsById(id)
                .map {
                    when (it) {
                        is Resource.Success -> Resource.Success(it.data.party)
                        is Resource.Error -> Resource.Error(it.errorData)
                    }
                }
    }

}