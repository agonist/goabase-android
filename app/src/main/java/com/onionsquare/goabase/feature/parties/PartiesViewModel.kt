package com.onionsquare.goabase.feature.parties

import androidx.lifecycle.*
import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.network.GoaBaseApi

class PartiesViewModel(private val api: GoaBaseApi) : ViewModel() {

    private val countryLiveData = MutableLiveData<String>()

    val parties: LiveData<List<Party>> = Transformations.switchMap(countryLiveData, this::fetchParties)
    val loading: MutableLiveData<Boolean> = MutableLiveData(false)

    private fun fetchParties(country: String): LiveData<List<Party>> {
        return liveData {
            loading.value = true
            val parties = api.getPartiesByCountry(country).parties
            emit(parties)
            loading.value = false
        }
    }

    fun setCountry(country: String) {
        countryLiveData.value = country
    }
}