package com.onionsquare.goabase.domain.usecase

import com.onionsquare.goabase.Result
import com.onionsquare.goabase.domain.repository.GoabaseRepository
import com.onionsquare.goabase.model.Party
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class PartyUseCase(private val remoteRepository: GoabaseRepository) {

    fun getPartyDetailsById(id: String): Flow<State<Party>> {
        return remoteRepository.getPartyDetailsById(id)
                .map {
                    when (it) {
                        is Result.Success -> State.Success(it.data.party)
                        is Result.Error -> State.Error(it.type)
                    }
                }
    }

}