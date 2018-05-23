package com.onionsquare.psyaround.feature.partydetails

import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.onionsquare.psyaround.R
import com.onionsquare.psyaround.feature.BaseActivity
import kotlinx.android.synthetic.main.parties.*

class PartyDetailsActivity : BaseActivity(), PartyDetailsView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun provideToolbarTitle(): String = getString(R.string.party_details)


    override fun provideLayout(): Int = R.layout.party_details

    override fun provideToolbar(): Toolbar = custom_toolbar as Toolbar
}