package com.onionsquare.psyaround.feature.parties

import com.onionsquare.psyaround.model.Party

interface PartiesView {

    fun showParties(parties: List<Party>)

    fun showLoader()

    fun hideLoader()
}