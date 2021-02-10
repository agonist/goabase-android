package com.onionsquare.goabase.feature.parties

import androidx.lifecycle.*
import com.onionsquare.goabase.domain.usecase.PartiesUseCase
import com.onionsquare.goabase.domain.usecase.State
import com.onionsquare.goabase.feature.partydetails.PartyDetailsActions
import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.singleEventFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart

class PartiesViewModel(private val useCase: PartiesUseCase) : ViewModel() {

    // STATE

    private val _country = MutableLiveData<String>()
    private val _refresh = MutableLiveData<Boolean>()

    private val country: LiveData<PartiesScreenState> = _country.distinctUntilChanged().switchMap { country ->
        fetchParties(country)
    }

    private val refresh: LiveData<PartiesScreenState> = _refresh.switchMap {
        fetchParties(_country.value!!)
    }

    val parties = MediatorLiveData<PartiesScreenState>().apply {
        addSource(country) { state -> value = state }
        addSource(refresh) { state -> value = state }
    }

    fun forceRefresh() {
        _country.value?.let {
            _refresh.value = true
        }
    }

    fun setCountry(country: String) {
        _country.value = country
    }

    private fun fetchParties(country: String): LiveData<PartiesScreenState> {
        return liveData {
            useCase.listPartiesByCountry(country)
                    .onStart { emit(PartiesScreenState.Loading) }
                    .collect { res ->
                        emit(when (res) {
                            is State.Error -> PartiesScreenState.Error
                            is State.Success -> PartiesScreenState.ListPartiesSuccess(res.data)
                        })
                    }
        }
    }

    // USER ACTIONS

    val userAction: SharedFlow<PartiesScreenActions> get() = _userActions
    private val _userActions = singleEventFlow<PartiesScreenActions>()

    fun onPartyClicked(party: Party) {
        _userActions.tryEmit(PartiesScreenActions.PartyClicked(party))
    }

    fun navigateUp() {
        _userActions.tryEmit(PartiesScreenActions.NavigateUp)
    }
}

sealed class PartiesScreenState {
    object Loading : PartiesScreenState()
    object Error : PartiesScreenState()
    data class ListPartiesSuccess(val parties: List<Party>) : PartiesScreenState()
}

sealed class PartiesScreenActions {
    data class PartyClicked(val party: Party) : PartiesScreenActions()
    object NavigateUp : PartiesScreenActions()
}