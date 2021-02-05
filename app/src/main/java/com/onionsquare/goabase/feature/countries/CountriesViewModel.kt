package com.onionsquare.goabase.feature.countries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onionsquare.goabase.domain.usecase.CountriesUseCase
import com.onionsquare.goabase.domain.usecase.State
import com.onionsquare.goabase.model.Country
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class CountriesViewModel(private val usecase: CountriesUseCase) : ViewModel() {

    val countries: LiveData<CountriesActions> get() = _countries
    private val _countries: MutableLiveData<CountriesActions> = MutableLiveData()

    fun fetchCountries(countriesParams: String = "list-all") {
        usecase.listAllCountriesSortedByPartiesAmount(countriesParams)
                .onStart { _countries.value = CountriesActions.Loading }
                .onEach { res ->
                    _countries.value =
                            when (res) {
                                is State.Error -> CountriesActions.Error
                                is State.Success -> CountriesActions.ListCountriesSuccess(res.data)
                            }
                }
                .launchIn(viewModelScope)
    }
}

sealed class CountriesActions {
    object Loading : CountriesActions()
    object Error : CountriesActions()
    data class ListCountriesSuccess(val countries: List<Country>) : CountriesActions()
}