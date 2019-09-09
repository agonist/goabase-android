package com.onionsquare.goabase.feature.parties

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.network.GoaBaseApi
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable

class PartiesViewModel(private val api: GoaBaseApi, val ioSheduler: Scheduler,
                       val mainScheduler: Scheduler) : ViewModel() {


    val parties: MutableLiveData<List<Party>> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val disposable: CompositeDisposable = CompositeDisposable()


    fun fetchParties(country: String) {
        disposable.add(api.getPartiesByCountry(country)
                .subscribeOn(ioSheduler)
                .observeOn(mainScheduler)
                .doOnSubscribe { loading.value = true }
                .subscribe({
                    loading.value = false
                    parties.value = it.parties
                }, {})
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}