package com.onionsquare.goabase.feature.partydetails

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.network.GoaBaseApi
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class PartyDetailsViewModel(private val api: GoaBaseApi, val ioSheduler: Scheduler,
                            val mainScheduler: Scheduler): ViewModel() {

    val party: MutableLiveData<Party> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val disposable: CompositeDisposable = CompositeDisposable()



    fun fetchParty(id: String) {
        disposable.add(api.getParty(id)
                .subscribeOn(ioSheduler)
                .observeOn(mainScheduler)
                .doOnSubscribe { loading.value = true }
                .subscribe({
                    loading.value = false
                    party.value = it.party
                }, {})
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}