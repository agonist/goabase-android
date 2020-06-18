package com.onionsquare.goabase.domain.repository

import com.onionsquare.goabase.Result
import com.onionsquare.goabase.model.Countries
import com.onionsquare.goabase.model.Parties
import com.onionsquare.goabase.model.PartyDetails
import kotlinx.coroutines.flow.Flow

interface GoabaseRepository {
    fun getCountries(params: String): Flow<Result<Countries>>

    fun getPartiesByCountry(country: String): Flow<Result<Parties>>

    fun getPartyDetailsById(partyId: String): Flow<Result<PartyDetails>>
}