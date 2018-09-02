package com.onionsquare.psyaround.feature.partydetails

import com.onionsquare.psyaround.model.Party

interface PartyDetailsView {

    fun showPartyDetails(details: Party)

    fun showLoader()

    fun hideLoader()

}