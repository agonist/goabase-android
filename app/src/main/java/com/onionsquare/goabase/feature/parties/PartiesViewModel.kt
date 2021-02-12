package com.onionsquare.goabase.feature.parties

import androidx.lifecycle.*
import com.onionsquare.goabase.domain.usecase.PartiesUseCase
import com.onionsquare.goabase.domain.usecase.State
import com.onionsquare.goabase.feature.partydetails.PartyDetailsActions
import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.singleEventFlow
import kotlinx.coroutines.flow.*

class PartiesViewModel(private val useCase: PartiesUseCase) : ViewModel() {

    // STATE

    private val _country = MutableLiveData<String>()

    val parties = MutableLiveData<PartiesScreenState>(PartiesScreenState.Init)

    fun setCountry(country: String) {
        _country.value?.let {} ?: kotlin.run {
            _country.value = country
            fetchParties()
        }
    }

    fun fetchParties() {
        useCase.listPartiesByCountry(_country.value!!)
                .onStart { parties.value = PartiesScreenState.Loading }
                .onEach { res ->
                    parties.value = when (res) {
                        is State.Error -> PartiesScreenState.Error
                        is State.Success -> PartiesScreenState.ListPartiesSuccess(res.data)
                    }
                }.launchIn(viewModelScope)
    }

    // USER ACTIONS

     val userActions = singleEventFlow<PartiesScreenActions>()

    fun onPartyClicked(party: Party) {
        userActions.tryEmit(PartiesScreenActions.PartyClicked(party))
    }

    fun navigateUp() {
        userActions.tryEmit(PartiesScreenActions.NavigateUp)
    }
}

sealed class PartiesScreenState {
    object Init : PartiesScreenState()
    object Loading : PartiesScreenState()
    object Error : PartiesScreenState()
    data class ListPartiesSuccess(val parties: List<Party>) : PartiesScreenState()
}

sealed class PartiesScreenActions {
    data class PartyClicked(val party: Party) : PartiesScreenActions()
    object NavigateUp : PartiesScreenActions()
}