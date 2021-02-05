package com.onionsquare.goabase.feature.parties

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onionsquare.goabase.domain.usecase.PartiesUseCase
import com.onionsquare.goabase.domain.usecase.State
import com.onionsquare.goabase.model.Party
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart

class PartiesViewModel(private val usecase: PartiesUseCase) : ViewModel() {

    val parties: LiveData<PartiesActions> get() = _parties
    private val _parties = MutableLiveData<PartiesActions>()

    fun fetchParties(country: String) {
        usecase.listPartiesByCountry(country)
                .onStart { _parties.value = PartiesActions.Loading }
                .onEach { res ->
                    _parties.value = when (res) {
                        is State.Error -> PartiesActions.Error
                        is State.Success -> PartiesActions.ListPartiesSuccess(res.data)
                    }
                }
                .launchIn(viewModelScope)
    }
}

sealed class PartiesActions {
    object Loading : PartiesActions()
    object Error : PartiesActions()
    data class ListPartiesSuccess(val parties: List<Party>) : PartiesActions()
}