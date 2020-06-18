package com.onionsquare.goabase.feature.parties

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.onionsquare.goabase.domain.usecase.PartiesUseCase
import com.onionsquare.goabase.domain.usecase.State
import com.onionsquare.goabase.model.Party
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class PartiesViewModel(private val usecase: PartiesUseCase) : ViewModel() {

    val parties: StateFlow<State<List<Party>>> get() = _parties
    private val _parties: MutableStateFlow<State<List<Party>>> =
            MutableStateFlow(State.Init)

    private fun fetchParties(country: String) {
        viewModelScope.launch {
            usecase.listPartiesByCountry(country)
                    .onStart { _parties.value = State.Loading }
                    .collect { res -> _parties.value = res }
        }
    }

    fun getPartiesByCountry(country: String) {
        fetchParties(country)
    }
}