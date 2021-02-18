package com.onionsquare.goabase.domain.usecase

import com.onionsquare.goabase.domain.repository.GoabaseRepository
import com.onionsquare.goabase.model.Country
import com.onionsquare.goabase.network.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class CountriesUseCase(private val remoteRepository: GoabaseRepository) {

    fun listAllCountriesSortedByPartiesAmount(params: String): Flow<Resource<List<Country>>> {
        return remoteRepository.getCountries(params)
                .map { res ->
                    when (res) {
                        is Resource.Success -> {
                            val sortedCountries = res.data.countries.sortedBy { it.numParties.toInt() }.asReversed()
                            Resource.Success(sortedCountries)
                        }
                        is Resource.Error -> Resource.Error(res.errorData)
                    }
                }
    }

}
