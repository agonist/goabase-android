package com.onionsquare.psyaround.feature.partydetails

import com.onionsquare.psyaround.network.GoaBaseApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PartyDetailsPresenter(val view: PartyDetailsView, val api: GoaBaseApi) {

    fun init(id: String) {
        api.getParty(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({

                }, {})
    }

}