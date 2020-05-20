package com.onionsquare

import com.onionsquare.goabase.model.*
import com.onionsquare.goabase.network.GoaBaseApi
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.Response

class DumbApiRepository : GoaBaseApi {

    val francePatrties = arrayListOf<Party>(
            Party("1", "mont-dor", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""),
            Party("2", "comte", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""),
            Party("3", "raclette", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
    )

    val italyParties = arrayListOf<Party>(
            Party("4", "pizza", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
    )


    val countries =
            listOf(
                    Country("France", "FR", "12", ""),
                    Country("Germany", "DE", "2", ""),
                    Country("Italy", "IT", "42", ""),
                    Country("Thailand", "TH", "0", "")
            )

    override suspend fun getCountries(country: String): Response<Countries> {
        if (country == "error") {
            val errorResponse = Response.error<Countries>(
                    404, ResponseBody.create(
                    "application/json".toMediaTypeOrNull(),
                    "{}")
            )
            return errorResponse
        }


        val response = Response.success<Countries>(Countries(countries))
        return response
    }

    override suspend fun getPartiesByCountry(country: String): Response<Parties> {
        return when (country) {
            "FR" -> Response.success<Parties>(Parties(francePatrties))
            "IT" -> Response.success<Parties>(Parties(italyParties))
            else -> Response.success<Parties>(Parties(listOf()))
        }
    }

    override suspend fun getParty(id: String): Response<PartyReply> {
        val l = arrayListOf<Party>()
        l.addAll(francePatrties)
        l.addAll(italyParties)

        return Response.success<PartyReply>(PartyReply(l.find { it.id == id }!!))
    }
}