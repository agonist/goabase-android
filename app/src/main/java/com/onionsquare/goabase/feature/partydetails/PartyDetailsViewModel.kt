package com.onionsquare.goabase.feature.partydetails

import androidx.lifecycle.*
import com.onionsquare.goabase.domain.usecase.PartyUseCase
import com.onionsquare.goabase.domain.usecase.State
import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.singleEventFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart

class PartyDetailsViewModel(private val useCase: PartyUseCase) : ViewModel() {

    // STATE

    private val _partyId = MutableLiveData<String>()
    private val _refresh = MutableLiveData<Boolean>()

    private val party: LiveData<PartyDetailsState> = _partyId.distinctUntilChanged().switchMap { partyId ->
        fetchPartyDetails(partyId)
    }

    private val refresh: LiveData<PartyDetailsState> = _refresh.switchMap {
        fetchPartyDetails(_partyId.value!!)
    }

    val partyDetails = MediatorLiveData<PartyDetailsState>().apply {
        addSource(party) { state -> value = state }
        addSource(refresh) { state -> value = state }
    }

    fun forceRefresh() {
        _partyId.value?.let {
            _refresh.value = true
        }
    }

    fun setPartyId(partyId: String){
        _partyId.value = partyId
    }

    private fun fetchPartyDetails(partyId: String): LiveData<PartyDetailsState> =
            liveData {
                useCase.getPartyDetailsById(partyId)
                        .onStart { emit(PartyDetailsState.Loading) }
                        .collect { res ->
                            emit(when (res) {
                                is State.Error -> PartyDetailsState.Error
                                is State.Success -> PartyDetailsState.GetPartyDetailsSuccess(res.data)
                            })
                        }
            }


    // USE ACTIONS

    val userActions = singleEventFlow<PartyDetailsActions>()

    fun navigateUp() {
        userActions.tryEmit(PartyDetailsActions.NavigateUp)
    }

}

sealed class PartyDetailsState {
    object Loading : PartyDetailsState()
    object Error : PartyDetailsState()
    data class GetPartyDetailsSuccess(val party: Party) : PartyDetailsState()
}

sealed class PartyDetailsActions {
    object NavigateUp : PartyDetailsActions()
}