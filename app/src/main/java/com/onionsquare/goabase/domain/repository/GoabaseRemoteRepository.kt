package com.onionsquare.goabase.domain.repository

import com.onionsquare.goabase.BaseRepository
import com.onionsquare.goabase.Result
import com.onionsquare.goabase.model.Countries
import com.onionsquare.goabase.model.Parties
import com.onionsquare.goabase.model.PartyDetails
import com.onionsquare.goabase.network.GoaBaseApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GoabaseRemoteRepository(private val api: GoaBaseApi) : BaseRepository(), GoabaseRepository {

    override fun getCountries(params: String): Flow<Result<Countries>> {
        return flow { emit(apiCall { api.getCountries(params) }) }
    }

    override fun getPartiesByCountry(country: String): Flow<Result<Parties>> =
            flow { emit(apiCall { api.getPartiesByCountry(country) }) }

    override fun getPartyDetailsById(partyId: String): Flow<Result<PartyDetails>> =
            flow { emit(apiCall { api.getParty(partyId) }) }
}