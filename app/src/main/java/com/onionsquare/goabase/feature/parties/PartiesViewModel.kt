package com.onionsquare.goabase.feature.parties

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onionsquare.goabase.domain.usecase.PartiesUseCase
import com.onionsquare.goabase.domain.usecase.State
import com.onionsquare.goabase.feature.countries.CountriesScreenAction
import com.onionsquare.goabase.model.Country
import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.singleEventFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class PartiesViewModel(private val usecase: PartiesUseCase) : ViewModel() {

    val parties: LiveData<PartiesScreenState> get() = _parties
    private val _parties = MutableLiveData<PartiesScreenState>()

    fun fetchParties(country: String) {
        usecase.listPartiesByCountry(country)
                .onStart { _parties.value = PartiesScreenState.Loading }
                .onEach { res ->
                    _parties.value = when (res) {
                        is State.Error -> PartiesScreenState.Error
                        is State.Success -> PartiesScreenState.ListPartiesSuccess(res.data)
                    }
                }
                .launchIn(viewModelScope)
    }

    // USER ACTIONS

    val userAction: SharedFlow<PartiesScreenActions> get() = _userActions
    private val _userActions = singleEventFlow<PartiesScreenActions>()

    fun onPartyClicked(party: Party) {
        _userActions.tryEmit(PartiesScreenActions.PartyClicked(party))
    }
}

sealed class PartiesScreenState {
    object Loading : PartiesScreenState()
    object Error : PartiesScreenState()
    data class ListPartiesSuccess(val parties: List<Party>) : PartiesScreenState()
}

sealed class PartiesScreenActions {
    data class PartyClicked(val party: Party): PartiesScreenActions()
}