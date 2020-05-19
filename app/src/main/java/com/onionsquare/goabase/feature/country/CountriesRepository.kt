package com.onionsquare.goabase.feature.country

import com.onionsquare.goabase.model.Country
import com.onionsquare.goabase.network.GoaBaseApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class CountriesRepository(private val api: GoaBaseApi) {

    fun listAllCountriesSortedByPartiesAmount(): Flow<List<Country>> =
            flow {
                val countries = api.getCountries("list-all")
                val sortedCountries = countries.countries.sortedBy { it.numParties.toInt() }.asReversed()
                emit(sortedCountries)
            }
}
