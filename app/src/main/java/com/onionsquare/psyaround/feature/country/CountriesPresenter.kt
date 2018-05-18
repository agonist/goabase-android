package com.onionsquare.psyaround.feature.country

import com.onionsquare.psyaround.network.GoaBaseApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CountriesPresenter(val view: CountriesView, val api: GoaBaseApi) {

    fun init() {
        api.getCountries("list-all")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                    view.showCountries(it.countries.sortedBy { it.numParties.toInt() }.asReversed())
                }, {})
    }

}