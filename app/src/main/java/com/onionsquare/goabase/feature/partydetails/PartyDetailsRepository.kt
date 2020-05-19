package com.onionsquare.goabase.feature.partydetails

import com.onionsquare.goabase.BaseRepository
import com.onionsquare.goabase.Result
import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.network.GoaBaseApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PartyDetailsRepository(private val api: GoaBaseApi) : BaseRepository() {

    fun getPartyDetailsById(partyId: String): Flow<PartyData<Party>> =
            flow {
                when (val result = apiCall { api.getParty(partyId) }) {
                    is Result.Success -> emit(PartyData.Success(result.data.party))
                    is Result.Error -> emit(PartyData.Error(result.exception))
                }
            }
}

sealed class PartyData<out T : Any> {
    data class Success(val party: Party) : PartyData<Party>()
    data class Error(val e: Exception) : PartyData<Nothing>()
}