package com.onionsquare.goabase.feature.countries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onionsquare.goabase.domain.usecase.CountriesUseCase
import com.onionsquare.goabase.domain.usecase.State
import com.onionsquare.goabase.model.Country
import com.onionsquare.goabase.singleEventFlow
import kotlinx.coroutines.flow.*

class CountriesViewModel(private val usecase: CountriesUseCase) : ViewModel() {

    // STATE

    val countries: LiveData<CountriesScreenState> get() = _countries
    private val _countries: MutableLiveData<CountriesScreenState> = MutableLiveData(CountriesScreenState.Init)


    init {
        fetchCountries()
    }

    fun fetchCountries(countriesParams: String = "list-all") {
        usecase.listAllCountriesSortedByPartiesAmount(countriesParams)
                .onStart { _countries.value = CountriesScreenState.Loading }
                .onEach { res ->
                    _countries.value =
                            when (res) {
                                is State.Error -> CountriesScreenState.Error
                                is State.Success -> CountriesScreenState.ListCountriesSuccess(res.data)
                            }
                }
                .launchIn(viewModelScope)
    }

    // USER ACTIONS

    val userAction: SharedFlow<CountriesScreenAction> get() = _userActions
    private val _userActions = singleEventFlow<CountriesScreenAction>()

    fun onCountryClicked(country: Country) {
        _userActions.tryEmit(CountriesScreenAction.CountryClicked(country))
    }

}

sealed class CountriesScreenState {
    object Init : CountriesScreenState()
    object Loading : CountriesScreenState()
    object Error : CountriesScreenState()
    data class ListCountriesSuccess(val countries: List<Country>) : CountriesScreenState()
}

sealed class CountriesScreenAction {
    data class CountryClicked(val country: Country): CountriesScreenAction()
}