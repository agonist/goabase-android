package com.onionsquare.goabase.feature.partydetails

import androidx.lifecycle.*
import com.hadilq.liveevent.LiveEvent
import com.onionsquare.goabase.model.Party
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class PartyDetailsViewModel(val partyDetailsRepository: PartyDetailsRepository) : ViewModel() {

    private val _loading = MutableLiveData<Boolean>()
    val loading = Transformations.map(_loading) { res -> res }

    private val _party = MutableLiveData<Party>()
    val party: LiveData<Party> = Transformations.map(_party) { res -> res }

    val error = LiveEvent<String>()

    private fun fetchPartyDetails(partyId: String) {
        viewModelScope.launch {
            partyDetailsRepository.getPartyDetailsById(partyId)
                    .onStart { _loading.value = true }
                    .onCompletion { _loading.value = false }
                    .collect { res ->
                        when (res) {
                            is PartyData.Success -> _party.value = res.party
                            is PartyData.Error -> error.value = "Unexpected error"
                        }
                    }
        }
    }

    fun getPartyById(partyId: String) {
        fetchPartyDetails(partyId)
    }
}