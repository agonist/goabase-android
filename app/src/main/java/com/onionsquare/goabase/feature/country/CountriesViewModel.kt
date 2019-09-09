package com.onionsquare.goabase.feature.country

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.onionsquare.goabase.model.Country
import com.onionsquare.goabase.network.GoaBaseApi
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable


class CountriesViewModel(private val api: GoaBaseApi, private val ioSheduler: Scheduler,
                         private val mainScheduler: Scheduler) : ViewModel() {

    val countries: MutableLiveData<List<Country>> = MutableLiveData()
    val loading: MutableLiveData<Boolean> = MutableLiveData(false)
    private val disposable: CompositeDisposable = CompositeDisposable()

    init {
        fetchCountries()
    }

    fun fetchCountries() {
        disposable.add(
                api.getCountries("list-all")
                        .subscribeOn(ioSheduler)
                        .observeOn(mainScheduler)
                        .doOnSubscribe { loading.value = true }
                        .subscribe({
                            loading.value = false
                            val sortedCountries = it.countries.sortedBy { it.numParties.toInt() }.asReversed()
                            countries.value = sortedCountries
                        }, {})
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }

}