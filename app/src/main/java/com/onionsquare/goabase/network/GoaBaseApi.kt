package com.onionsquare.goabase.network

import com.onionsquare.goabase.model.Countries
import com.onionsquare.goabase.model.Parties
import com.onionsquare.goabase.model.PartyDetails
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoaBaseApi {

    @GET("/api/party/json/")
    fun getCountries(@Query("country") country: String): Flow<Resource<Countries>>

    @GET("/api/party/json/")
    fun getPartiesByCountry(@Query("country") country: String): Flow<Resource<Parties>>

    @GET("/api/party/json/{id}")
    fun getParty(@Path("id") id: String): Flow<Resource<PartyDetails>>

}