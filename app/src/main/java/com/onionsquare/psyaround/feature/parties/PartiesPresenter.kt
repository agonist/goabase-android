package com.onionsquare.psyaround.feature.parties

import com.onionsquare.psyaround.network.GoaBaseApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PartiesPresenter(val view: PartiesView, val api: GoaBaseApi) {

    fun init(country: String) {
        api.getPartiesByCountry(country)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    view.showParties(it.parties)
                }, {})
    }

}