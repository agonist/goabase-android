package com.onionsquare

import com.onionsquare.goabase.model.*
import com.onionsquare.goabase.network.GoaBaseApi

class DumbApiRepository : GoaBaseApi {

    override suspend fun getCountries(country: String): Countries {
        return Countries(
                listOf(
                        Country("France", "FR", "12", ""),
                        Country("Germany", "DE", "2", ""),
                        Country("Italy", "IT", "42", ""),
                        Country("Thailand", "TH", "0", "")
                )
        )
    }

    override suspend fun getPartiesByCountry(country: String): Parties {

        val francePatrties = listOf<Party>(
                Party("", "mont-dor", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""),
                Party("", "comte", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""),
                Party("", "raclette", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
        )

        val italyParties = listOf<Party>(
                Party("", "pizza", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
        )

        return when (country) {
            "FR" -> Parties(francePatrties)
            "IT" -> Parties(italyParties)
            else -> Parties(listOf())
        }
    }

    override suspend fun getParty(id: String): PartyReply {
        TODO("Not yet implemented")
    }
}