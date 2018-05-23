package com.onionsquare.psyaround.feature.parties

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import com.heetch.countrypicker.Utils
import com.onionsquare.psyaround.PsyApp
import com.onionsquare.psyaround.R
import com.onionsquare.psyaround.feature.BaseActivity
import com.onionsquare.psyaround.feature.Divider
import com.onionsquare.psyaround.feature.HeaderViewDecoration
import com.onionsquare.psyaround.feature.partydetails.PartyDetailsActivity
import com.onionsquare.psyaround.model.Party
import kotlinx.android.synthetic.main.header.view.*
import kotlinx.android.synthetic.main.parties.*

class PartiesActivity : BaseActivity(), PartiesView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parties_recycler.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        parties_recycler.layoutManager = layoutManager
        val country = intent.getStringExtra("COUNTRY_NAME")
        displayBackArrow(true)
        PartiesPresenter(this, PsyApp.instance.api).init(country)
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

    override fun provideLayout(): Int = R.layout.parties

    override fun provideToolbar(): Toolbar = custom_toolbar as Toolbar

    override fun provideToolbarTitle(): String {
        return ""
    }

}