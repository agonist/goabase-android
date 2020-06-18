package com.onionsquare.goabase.domain.usecase

import com.onionsquare.goabase.Result
import com.onionsquare.goabase.domain.repository.GoabaseRepository
import com.onionsquare.goabase.model.Country
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class CountriesUseCase(private val remoteRepository: GoabaseRepository) {

    fun listAllCountriesSortedByPartiesAmount(params: String): Flow<State<List<Country>>> {
        return remoteRepository.getCountries(params)
                .map { res ->
                    when (res) {
                        is Result.Success -> {
                            val sortedCountries = res.data.countries.sortedBy { it.numParties.toInt() }.asReversed()
                            State.Success(sortedCountries)
                        }
                        is Result.Error -> {
                            State.Error(res.type)
                        }
                    }
                }
    }
}
