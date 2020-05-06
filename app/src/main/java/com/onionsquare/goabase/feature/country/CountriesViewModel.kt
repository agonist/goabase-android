package com.onionsquare.goabase.feature.country

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.onionsquare.goabase.model.Country
import com.onionsquare.goabase.network.GoaBaseApi


class CountriesViewModel(private val api: GoaBaseApi) : ViewModel() {

    val loading: MutableLiveData<Boolean> = MutableLiveData(false)

    val countries: LiveData<List<Country>> = liveData {
        loading.value = true
        val countries = api.getCountries("list-all")
        val sortedCountries = countries.countries.sortedBy { it.numParties.toInt() }.asReversed()
        emit(sortedCountries)
        loading.value = false
    }
}