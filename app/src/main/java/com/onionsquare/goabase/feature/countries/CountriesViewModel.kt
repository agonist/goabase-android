package com.onionsquare.goabase.feature.countries

import androidx.lifecycle.*
import com.hadilq.liveevent.LiveEvent
import com.onionsquare.goabase.model.Country
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class CountriesViewModel(private val countriesRepository: CountriesRepository) : ViewModel() {

    private val _countries = MutableLiveData<List<Country>>()
    val countries: LiveData<List<Country>> = Transformations.map(_countries) { res -> res }

    private val _loading = MutableLiveData<Boolean>()
    val loading = Transformations.map(_loading) { res -> res }

    val error = LiveEvent<String>()


    fun getCountriesAll() {
        fetchCountries("list-all")
    }

    private fun fetchCountries(countriesParams: String) {
        viewModelScope.launch {
            countriesRepository.listAllCountriesSortedByPartiesAmount(countriesParams)
                    .onStart { _loading.value = true }
                    .onCompletion { _loading.value = false }
                    .collect { res ->
                        when (res) {
                            is CountriesData.Success -> _countries.value = res.countries
                            is CountriesData.Error -> error.value = "Unexpected error"
                        }
                    }
        }
    }
}

