package com.onionsquare.goabase.feature.parties

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.onionsquare.goabase.PsyApp
import com.onionsquare.goabase.R
import com.onionsquare.goabase.feature.BaseActivity
import com.onionsquare.goabase.feature.Divider
import com.onionsquare.goabase.feature.country.CountriesActivity
import com.onionsquare.goabase.feature.partydetails.PartyDetailsActivity
import com.onionsquare.goabase.model.Party
import kotlinx.android.synthetic.main.parties.*

class PartiesActivity : BaseActivity(), PartiesView {

    companion object {
        val PARTY_ID_EXTRA = "PARTY_ID"
    }

    var presenter: PartiesPresenter? = null
    var parties: ArrayList<Party>? = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.apply {
            val country = intent.getStringExtra(CountriesActivity.COUNTRY_NAME_EXTRA)
            title = "Parties in $country"
        }
        parties_recycler.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        parties_recycler.layoutManager = layoutManager
        val country = intent.getStringExtra(CountriesActivity.COUNTRY_NAME_EXTRA)
        displayBackArrow(true)
        presenter = PartiesPresenter(this, PsyApp.instance.api)
        presenter?.init(country)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.end()
    }

    override fun showParties(parties: List<Party>) {
        this.parties?.addAll(parties)
        parties_recycler.addItemDecoration(Divider(applicationContext))
        parties_recycler.adapter = PartiesAdapter(parties, object : PartiesAdapter.PartyClickListener {
            override fun onClick(party: Party) {
                val intent: Intent = Intent(this@PartiesActivity, PartyDetailsActivity::class.java)
                intent.putExtra(PARTY_ID_EXTRA, party.id)
                startActivity(intent)
            }
        })

    }

    override fun showLoader() {
        parties_progress.visibility = View.VISIBLE
        parties_recycler.visibility = View.GONE
    }

    override fun hideLoader() {
        parties_progress.visibility = View.GONE
        parties_recycler.visibility = View.VISIBLE
    }

    override fun provideLayout(): Int = R.layout.parties

    override fun provideToolbar(): Toolbar = custom_toolbar as Toolbar
}