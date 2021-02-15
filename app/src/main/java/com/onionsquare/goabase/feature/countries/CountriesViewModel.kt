package com.onionsquare.goabase.feature.countries

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onionsquare.goabase.domain.usecase.CountriesUseCase
import com.onionsquare.goabase.model.Country
import com.onionsquare.goabase.network.Resource
import com.onionsquare.goabase.singleEventFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class CountriesViewModel(private val useCase: CountriesUseCase) : ViewModel() {

    // STATE

    val countries = MutableLiveData<CountriesScreenState>(CountriesScreenState.Init)

    fun fetchCountries(countriesParams: String = "list-all") {
        useCase.listAllCountriesSortedByPartiesAmount(countriesParams)
                .onStart { countries.value = CountriesScreenState.Loading }
                .onEach { res ->
                    countries.value = when (res) {
                        is Resource.Error -> CountriesScreenState.Error
                        is Resource.Success -> CountriesScreenState.ListCountriesSuccess(res.data)
                    }
                }.launchIn(viewModelScope)
    }

    // USER ACTIONS

    val userActions = singleEventFlow<CountriesScreenAction>()

    fun onCountryClicked(country: Country) {
        userActions.tryEmit(CountriesScreenAction.CountryClicked(country))
    }

}

sealed class CountriesScreenState {
    object Init : CountriesScreenState()
    object Loading : CountriesScreenState()
    object Error : CountriesScreenState()
    data class ListCountriesSuccess(val countries: List<Country>) : CountriesScreenState()
}

sealed class CountriesScreenAction {
    data class CountryClicked(val country: Country) : CountriesScreenAction()
}