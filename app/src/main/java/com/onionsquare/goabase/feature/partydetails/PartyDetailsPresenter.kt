package com.onionsquare.goabase.feature.partydetails

import com.onionsquare.goabase.network.GoaBaseApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class PartyDetailsPresenter(val view: PartyDetailsView, val api: GoaBaseApi) {

    var disp: Disposable? = null

    fun init(id: String) {
        disp = api.getParty(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { view.showLoader() }
                .subscribe({
                    view.hideLoader()
                    view.showPartyDetails(it.party)
                }, {})
    }

    fun end() {
        disp?.dispose()
    }

}