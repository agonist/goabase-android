package com.onionsquare.goabase.feature.partydetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onionsquare.goabase.domain.usecase.PartyUseCase
import com.onionsquare.goabase.domain.usecase.State
import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.singleEventFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class PartyDetailsViewModel(private val useCase: PartyUseCase) : ViewModel() {

    // STATE

    private val _partyId = MutableLiveData<String>()

    val partyDetails = MutableLiveData<PartyDetailsState>(PartyDetailsState.Init)

    fun setPartyId(partyId: String) {
        _partyId.value = partyId
        fetchPartyDetails()
    }

    fun fetchPartyDetails() {
        useCase.getPartyDetailsById(_partyId.value!!)
                .onStart { partyDetails.value = PartyDetailsState.Loading }
                .onEach { res ->
                    partyDetails.value = when (res) {
                        is State.Error -> PartyDetailsState.Error
                        is State.Success -> PartyDetailsState.GetPartyDetailsSuccess(res.data)
                    }
                }.launchIn(viewModelScope)
    }


    // USE ACTIONS

    val userActions = singleEventFlow<PartyDetailsActions>()

    fun navigateUp() {
        userActions.tryEmit(PartyDetailsActions.NavigateUp)
    }

}

sealed class PartyDetailsState {
    object Init : PartyDetailsState()
    object Loading : PartyDetailsState()
    object Error : PartyDetailsState()
    data class GetPartyDetailsSuccess(val party: Party) : PartyDetailsState()
}

sealed class PartyDetailsActions {
    object NavigateUp : PartyDetailsActions()
}