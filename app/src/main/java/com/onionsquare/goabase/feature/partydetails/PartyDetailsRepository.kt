package com.onionsquare.goabase.feature.partydetails

import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.network.GoaBaseApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PartyDetailsRepository(private val api: GoaBaseApi) {

    fun getPartyDetailsById(partyId: String): Flow<Party> =
            flow {
                val party = api.getParty(partyId).party
                emit(party)
            }
}