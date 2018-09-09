package com.onionsquare.goabase.network

import com.onionsquare.goabase.model.Countries
import com.onionsquare.goabase.model.Parties
import com.onionsquare.goabase.model.PartyReply
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GoaBaseApi {

    @GET("/api/party/json/")
    fun getCountries(@Query("country") country: String): Observable<Countries>

    @GET("/api/party/json/")
    fun getPartiesByCountry(@Query("country") country: String): Observable<Parties>

    @GET("/api/party/json/{id}")
    fun getParty(@Path("id") id: String): Observable<PartyReply>

}