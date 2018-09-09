package com.onionsquare.goabase.feature.parties

import com.onionsquare.goabase.model.Party

interface PartiesView {

    fun showParties(parties: List<Party>)

    fun showLoader()

    fun hideLoader()
}