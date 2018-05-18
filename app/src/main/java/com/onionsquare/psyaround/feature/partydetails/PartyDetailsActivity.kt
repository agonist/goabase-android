package com.onionsquare.psyaround.feature.partydetails

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.onionsquare.psyaround.R

class PartyDetailsActivity : AppCompatActivity(), PartyDetailsView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.party_details)


    }
}