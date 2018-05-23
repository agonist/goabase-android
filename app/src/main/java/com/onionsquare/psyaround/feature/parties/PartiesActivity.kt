package com.onionsquare.psyaround.feature.parties

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import com.onionsquare.psyaround.PsyApp
import com.onionsquare.psyaround.R
import com.onionsquare.psyaround.feature.BaseActivity
import com.onionsquare.psyaround.feature.Divider
import com.onionsquare.psyaround.model.Party
import kotlinx.android.synthetic.main.parties.*

class PartiesActivity : BaseActivity(), PartiesView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        parties_recycler.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        parties_recycler.layoutManager = layoutManager
        val country = intent.getStringExtra("COUNTRY_NAME")
        PartiesPresenter(this, PsyApp.instance.api).init(country)
    }

    override fun showParties(parties: List<Party>) {
        parties_recycler.addItemDecoration(Divider(
                getApplicationContext()
        ));
        parties_recycler.adapter = PartiesAdapter(parties, object : PartiesAdapter.PartyClickListener {
            override fun onClick(party: Party) {

            }
        })

    }

    override fun provideLayout(): Int = R.layout.parties

    override fun provideToolbar(): Toolbar = custom_toolbar as Toolbar

    override fun provideToolbarTitle(): String {
        val country = intent.getStringExtra("COUNTRY_NAME")
        return "Parties in $country"
    }

}