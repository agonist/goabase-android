package com.onionsquare.goabase.feature.country

import com.onionsquare.goabase.network.GoaBaseApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class CountriesPresenter(val view: CountriesView, val api: GoaBaseApi) {

    var disp :Disposable? = null

    fun init() {
       disp =  api.getCountries("list-all")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoader() }
                .subscribe({
                    view.hideLoader()
                    view.showCountries(it.countries.sortedBy { it.numParties.toInt() }.asReversed())
                }, {})
    }

    fun end() {
        disp?.dispose()
    }

}