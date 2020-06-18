package com.onionsquare.goabase.feature.countries

import androidx.lifecycle.*
import com.onionsquare.goabase.domain.usecase.CountriesUseCase
import com.onionsquare.goabase.domain.usecase.State
import com.onionsquare.goabase.model.Countries
import com.onionsquare.goabase.model.Country
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CountriesViewModel(private val usecase: CountriesUseCase) : ViewModel() {

    val countries: StateFlow<State<List<Country>>> get() = _countries
    private val _countries: MutableStateFlow<State<List<Country>>> =
            MutableStateFlow(State.Init)

    fun getCountriesAll() {
        fetchCountries("list-all")
    }

    private fun fetchCountries(countriesParams: String) {
        viewModelScope.launch {
            usecase.listAllCountriesSortedByPartiesAmount(countriesParams)
                    .onStart { _countries.value = State.Loading }
                    .collect { res -> _countries.value = res }
        }
    }
}

