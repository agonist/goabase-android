package com.onionsquare

import com.onionsquare.goabase.model.Countries
import com.onionsquare.goabase.model.Country
import com.onionsquare.goabase.model.Parties
import com.onionsquare.goabase.model.PartyReply
import com.onionsquare.goabase.network.GoaBaseApi

class DumbApiRepository: GoaBaseApi {

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
        TODO("Not yet implemented")
    }

    override suspend fun getParty(id: String): PartyReply {
        TODO("Not yet implemented")
    }
}