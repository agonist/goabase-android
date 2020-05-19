package com.onionsquare.goabase.feature.parties

import androidx.lifecycle.*
import com.onionsquare.goabase.model.Party
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

@ExperimentalCoroutinesApi
class PartiesViewModel(val partiesRepository: PartiesRepository) : ViewModel() {

    private val countryLiveData = MutableLiveData<String>()
    val loading: MutableLiveData<Boolean> = MutableLiveData()

    val parties: LiveData<List<Party>> = Transformations.switchMap(countryLiveData, this::fetchParties)

    private fun fetchParties(country: String): LiveData<List<Party>> =
            partiesRepository.getPartiesByCountry(country)
                    .onStart { loading.value = true }
                    .onCompletion { loading.value = false }
                    .asLiveData()

    fun setCountry(country: String) {
        countryLiveData.postValue(country)
    }
}