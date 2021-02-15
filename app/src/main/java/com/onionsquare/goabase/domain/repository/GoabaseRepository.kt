package com.onionsquare.goabase.domain.repository

import com.onionsquare.goabase.model.Countries
import com.onionsquare.goabase.model.Parties
import com.onionsquare.goabase.model.PartyDetails
import com.onionsquare.goabase.network.Resource
import kotlinx.coroutines.flow.Flow

interface GoabaseRepository {
    fun getCountries(params: String): Flow<Resource<Countries>>

    fun getPartiesByCountry(country: String): Flow<Resource<Parties>>

    fun getPartyDetailsById(partyId: String): Flow<Resource<PartyDetails>>
}