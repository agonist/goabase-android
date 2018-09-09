package com.onionsquare.goabase.feature.parties

import com.onionsquare.goabase.network.GoaBaseApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PartiesPresenter(val view: PartiesView, val api: GoaBaseApi) {

    var disp: Disposable? = null

    fun init(country: String) {
        disp = api.getPartiesByCountry(country)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoader() }
                .subscribe({
                    view.hideLoader()
                    view.showParties(it.parties)
                }, {})
    }

    fun end() {
        disp?.dispose()
    }

}