package com.onionsquare.goabase.feature.parties

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.heetch.countrypicker.Utils
import com.onionsquare.goabase.PsyApp
import com.onionsquare.goabase.R
import com.onionsquare.goabase.feature.BaseActivity
import com.onionsquare.goabase.feature.Divider
import com.onionsquare.goabase.feature.HeaderViewDecoration
import com.onionsquare.goabase.feature.partydetails.PartyDetailsActivity
import com.onionsquare.goabase.model.Party
import kotlinx.android.synthetic.main.header.view.*
import kotlinx.android.synthetic.main.parties.*

class PartiesActivity : BaseActivity(), PartiesView {

    var presenter: PartiesPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parties_recycler.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        parties_recycler.layoutManager = layoutManager
        val country = intent.getStringExtra("COUNTRY_NAME")
        displayBackArrow(true)
        presenter = PartiesPresenter(this, PsyApp.instance.api)
        presenter?.init(country)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter?.end()
    }

    override fun showParties(parties: List<Party>) {
        val view = layoutInflater.inflate(R.layout.header, null)
        val country = intent.getStringExtra("COUNTRY_NAME")
        val iso = intent.getStringExtra("COUNTRY_ISO")

        view.name_header.text = "Parties in $country"
        view.flag_header.setImageDrawable(getDrawable(Utils.getMipmapResId(this, iso.toLowerCase() + "_flag")))
        parties_recycler.addItemDecoration(Divider(applicationContext))
        parties_recycler.addItemDecoration(HeaderViewDecoration(view))
        parties_recycler.adapter = PartiesAdapter(parties, object : PartiesAdapter.PartyClickListener {
            override fun onClick(party: Party) {
                val intent: Intent = Intent(this@PartiesActivity, PartyDetailsActivity::class.java)
                intent.putExtra("PARTY_ID", party.id)
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

    override fun provideToolbarTitle(): String {
        return ""
    }

}