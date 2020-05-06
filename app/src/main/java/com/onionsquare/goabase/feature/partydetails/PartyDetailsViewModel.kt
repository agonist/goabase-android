package com.onionsquare.goabase.feature.partydetails

import androidx.lifecycle.*
import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.network.GoaBaseApi

class PartyDetailsViewModel(private val api: GoaBaseApi) : ViewModel() {

    private val partyIdLiveData = MutableLiveData<String>()

    val party: LiveData<Party> = Transformations.switchMap(partyIdLiveData, this::fetchPartyDetails)
    val loading: MutableLiveData<Boolean> = MutableLiveData(false)

    private fun fetchPartyDetails(partyId: String): LiveData<Party> {
        return liveData {
            loading.value = true
            val party = api.getParty(partyId).party
            emit(party)
            loading.value = false
        }
    }

    fun setPartyId(partyId: String) {
        partyIdLiveData.value = partyId
    }
}