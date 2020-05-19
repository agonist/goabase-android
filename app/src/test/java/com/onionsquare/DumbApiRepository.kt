package com.onionsquare

import com.onionsquare.goabase.model.*
import com.onionsquare.goabase.network.GoaBaseApi

class DumbApiRepository : GoaBaseApi {

    val francePatrties = arrayListOf<Party>(
            Party("1", "mont-dor", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""),
            Party("2", "comte", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""),
            Party("3", "raclette", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
    )

    val italyParties = arrayListOf<Party>(
            Party("4", "pizza", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "")
    )

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
        return when (country) {
            "FR" -> Parties(francePatrties)
            "IT" -> Parties(italyParties)
            else -> Parties(listOf())
        }
    }

    override suspend fun getParty(id: String): PartyReply {
        val l = arrayListOf<Party>()
        l.addAll(francePatrties)
        l.addAll(italyParties)

        return PartyReply(l.find { it.id == id }!!)
    }
}