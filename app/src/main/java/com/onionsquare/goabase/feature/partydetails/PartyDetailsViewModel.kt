package com.onionsquare.goabase.feature.partydetails

import androidx.lifecycle.*
import com.onionsquare.goabase.model.Party
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart

@ExperimentalCoroutinesApi
class PartyDetailsViewModel(val partyDetailsRepository: PartyDetailsRepository) : ViewModel() {

    private val partyIdLiveData = MutableLiveData<String>()
    val loading: MutableLiveData<Boolean> = MutableLiveData()

    val party: LiveData<Party> = Transformations.switchMap(partyIdLiveData, this::fetchPartyDetails)

    private fun fetchPartyDetails(partyId: String): LiveData<Party> {
        return partyDetailsRepository.getPartyDetailsById(partyId)
                .onStart { loading.value = true }
                .onCompletion { loading.value = false }
                .asLiveData()
    }

    fun setPartyId(partyId: String) {
        partyIdLiveData.value = partyId
    }
}