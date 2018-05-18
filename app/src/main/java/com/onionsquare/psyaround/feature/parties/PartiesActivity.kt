package com.onionsquare.psyaround.feature.parties

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.onionsquare.psyaround.PsyApp
import com.onionsquare.psyaround.R
import com.onionsquare.psyaround.model.Party
import kotlinx.android.synthetic.main.parties.*

class PartiesActivity : AppCompatActivity(), PartiesView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.parties)
        parties_recycler.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        parties_recycler.layoutManager = layoutManager
        val country = intent.getStringExtra("COUNTRY_NAME")
        title = "Parties in $country"
        PartiesPresenter(this, PsyApp.instance.api).init(country)
    }

    override fun showParties(parties: List<Party>) {

        parties_recycler.adapter = PartiesAdapter(parties, object : PartiesAdapter.PartyClickListener {
            override fun onClick(party: Party) {

            }
        })

    }
}