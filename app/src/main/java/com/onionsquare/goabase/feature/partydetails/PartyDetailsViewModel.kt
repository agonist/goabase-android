package com.onionsquare.goabase.feature.partydetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onionsquare.goabase.domain.usecase.PartyUseCase
import com.onionsquare.goabase.domain.usecase.State
import com.onionsquare.goabase.model.Party
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PartyDetailsViewModel(val usecase: PartyUseCase) : ViewModel() {

    val party: StateFlow<State<Party>> get() = _party
    private val _party: MutableStateFlow<State<Party>> =
            MutableStateFlow(State.Init)

    private fun fetchPartyDetails(partyId: String) {
        viewModelScope.launch {
            usecase.getPartyDetailsById(partyId)
                    .onStart { _party.value = State.Loading }
                    .collect { res -> _party.value = res }
        }
    }

    fun getPartyById(partyId: String) {
        fetchPartyDetails(partyId)
    }
}