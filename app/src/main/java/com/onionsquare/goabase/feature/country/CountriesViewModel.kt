package com.onionsquare.goabase.feature.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.onionsquare.goabase.model.Country
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

@ExperimentalCoroutinesApi
class CountriesViewModel(countriesRepository: CountriesRepository) : ViewModel() {

    val loading: MutableLiveData<Boolean> = MutableLiveData()

    val countries: LiveData<List<Country>> = countriesRepository.listAllCountriesSortedByPartiesAmount()
            .onStart { loading.value = true }
            .onCompletion { loading.value = false }
            .asLiveData()
}

