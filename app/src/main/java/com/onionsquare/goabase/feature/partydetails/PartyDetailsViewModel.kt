package com.onionsquare.goabase.feature.partydetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onionsquare.goabase.domain.usecase.PartyUseCase
import com.onionsquare.goabase.domain.usecase.State
import com.onionsquare.goabase.model.Party
import kotlinx.coroutines.flow.*

class PartyDetailsViewModel(private val usecase: PartyUseCase) : ViewModel() {

    val party: LiveData<PartyDetailsAction> get() = _party
    private val _party = MutableLiveData<PartyDetailsAction>()

    private fun fetchPartyDetails(partyId: String) {
            usecase.getPartyDetailsById(partyId)
                    .onStart { _party.value = PartyDetailsAction.Loading }
                    .onEach { res -> _party.value = when (res) {
                        is State.Error -> PartyDetailsAction.Error
                        is State.Success -> PartyDetailsAction.GetPartyDetailsSuccess(res.data)
                    } }
                    .launchIn(viewModelScope)
    }

    fun getPartyById(partyId: String) {
        fetchPartyDetails(partyId)
    }
}

sealed class PartyDetailsAction {
    object Loading : PartyDetailsAction()
    object Error : PartyDetailsAction()
    data class GetPartyDetailsSuccess(val party: Party) : PartyDetailsAction()
}