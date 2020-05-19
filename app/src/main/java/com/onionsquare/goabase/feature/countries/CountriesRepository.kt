package com.onionsquare.goabase.feature.countries

import com.onionsquare.goabase.BaseRepository
import com.onionsquare.goabase.Result
import com.onionsquare.goabase.model.Country
import com.onionsquare.goabase.network.GoaBaseApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class CountriesRepository(private val api: GoaBaseApi) : BaseRepository() {

    fun listAllCountriesSortedByPartiesAmount(params: String): Flow<CountriesData<Any>> =
            flow {
                when (val result = apiCall { api.getCountries(params) }) {
                    is Result.Success -> {
                        val sortedCountries = result.data.countries.sortedBy { it.numParties.toInt() }.asReversed()
                        emit(CountriesData.Success(sortedCountries))
                    }
                    is Result.Error -> {
                        emit(CountriesData.Error(result.exception))
                    }
                }
            }
}

sealed class CountriesData<out T : Any> {
    data class Success(val countries: List<Country>) : CountriesData<List<Country>>()
    data class Error(val e: Exception) : CountriesData<Nothing>()
}