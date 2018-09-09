package com.onionsquare.goabase.feature.partydetails

import com.onionsquare.goabase.model.Party

interface PartyDetailsView {

    fun showPartyDetails(details: Party)

    fun showLoader()

    fun hideLoader()

}