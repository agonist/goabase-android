package com.onionsquare.goabase.feature.parties

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.onionsquare.goabase.R
import com.onionsquare.goabase.feature.BaseActivity
import com.onionsquare.goabase.feature.Divider
import com.onionsquare.goabase.feature.country.CountriesActivity
import com.onionsquare.goabase.feature.partydetails.PartyDetailsActivity
import com.onionsquare.goabase.model.Party
import kotlinx.android.synthetic.main.parties.*

class PartiesOfflineActivity : BaseActivity() {

    companion object {
        val PARTY_ID_EXTRA = "PARTY_ID"
        val PARTIES_OFFLINE = "PARTIES_OFFLINE"
    }

    var parties: ArrayList<Party>? = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            val country = intent.getStringExtra(CountriesActivity.COUNTRY_NAME_EXTRA)
            title = "Parties"
        }
        parties_recycler.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        parties_recycler.layoutManager = layoutManager
        displayBackArrow(true)
        val list = intent.getSerializableExtra(PARTIES_OFFLINE) as ArrayList<Party>
        showParties(list)
        hideLoader()
    }

    private fun showParties(parties: List<Party>) {
        this.parties?.addAll(parties)
        parties_recycler.addItemDecoration(Divider(applicationContext))
        parties_recycler.adapter = PartiesAdapter(parties, object : PartiesAdapter.PartyClickListener {
            override fun onClick(party: Party) {
                val intent: Intent = Intent(this@PartiesOfflineActivity, PartyDetailsActivity::class.java)
                intent.putExtra(PARTY_ID_EXTRA, party.id)
                startActivity(intent)
            }
        })

    }

    fun hideLoader() {
        parties_progress.visibility = View.GONE
        parties_recycler.visibility = View.VISIBLE
    }

    override fun provideLayout(): Int = R.layout.parties

    override fun provideToolbar(): Toolbar = custom_toolbar as Toolbar
}