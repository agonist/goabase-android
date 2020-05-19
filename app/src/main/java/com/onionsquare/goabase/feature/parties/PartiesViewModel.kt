package com.onionsquare.goabase.feature.parties

import androidx.lifecycle.*
import com.hadilq.liveevent.LiveEvent
import com.onionsquare.goabase.model.Party
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class PartiesViewModel(val partiesRepository: PartiesRepository) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading = Transformations.map(_loading) { res -> res }

    private val _parties = MutableLiveData<List<Party>>()
    val parties: LiveData<List<Party>> = Transformations.map(_parties) { res -> res }

    val error = LiveEvent<String>()

    private fun fetchParties(country: String) {
        viewModelScope.launch {
            partiesRepository.getPartiesByCountry(country)
                    .onStart { _loading.value = true }
                    .onCompletion { _loading.value = false }
                    .collect { res ->
                        when (res) {
                            is PartiesData.Success -> _parties.value = res.countries
                            is PartiesData.Error -> error.value = "Unexpected error"
                        }
                    }
        }
    }

    fun getPartiesByCountry(country: String) {
        fetchParties(country)
    }
}