package com.onionsquare.goabase.network

import com.onionsquare.goabase.model.Countries
import com.onionsquare.goabase.model.Parties
import com.onionsquare.goabase.model.PartyDetails
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoaBaseApi {

    @GET("/api/party/json/")
    suspend fun getCountries(@Query("country") country: String): Response<Countries>

    @GET("/api/party/json/")
    suspend fun getPartiesByCountry(@Query("country") country: String): Response<Parties>

    @GET("/api/party/json/{id}")
    suspend fun getParty(@Path("id") id: String): Response<PartyDetails>

}