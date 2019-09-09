package com.onionsquare.goabase.feature.parties

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.onionsquare.goabase.R
import com.onionsquare.goabase.feature.BaseActivity
import com.onionsquare.goabase.feature.Divider
import com.onionsquare.goabase.feature.country.CountriesActivity
import com.onionsquare.goabase.feature.partydetails.PartyDetailsActivity
import com.onionsquare.goabase.model.Party
import kotlinx.android.synthetic.main.parties.*
import org.koin.android.ext.android.inject

class PartiesActivity : BaseActivity() {

    companion object {
        const val PARTY_ID_EXTRA = "PARTY_ID"
    }

    private val viewModel: PartiesViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()

        val country = intent.getStringExtra(CountriesActivity.COUNTRY_NAME_EXTRA)

        viewModel.parties.observe(this, Observer {
            showParties(it)
        })

        viewModel.loading.observe(this, Observer {
            when (it) {
                false -> hideLoader()
                true -> showLoader()
            }
        })

        viewModel.fetchParties(country)
    }

    private fun initUI() {
        supportActionBar?.apply {
            val country = intent.getStringExtra(CountriesActivity.COUNTRY_NAME_EXTRA)
            title = "Parties in $country"
        }
        parties_recycler.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        parties_recycler.layoutManager = layoutManager
        displayBackArrow(true)
    }

    private fun showParties(parties: List<Party>) {
        parties_recycler.addItemDecoration(Divider(applicationContext))
        parties_recycler.adapter = PartiesAdapter(parties, object : PartiesAdapter.PartyClickListener {
            override fun onClick(party: Party) {
                val intent: Intent = Intent(this@PartiesActivity, PartyDetailsActivity::class.java)
                intent.putExtra(PARTY_ID_EXTRA, party.id)
                startActivity(intent)
            }
        })

    }

    private fun showLoader() {
        parties_progress.visibility = View.VISIBLE
        parties_recycler.visibility = View.GONE
    }

    private fun hideLoader() {
        parties_progress.visibility = View.GONE
        parties_recycler.visibility = View.VISIBLE
    }

    override fun provideLayout(): Int = R.layout.parties

    override fun provideToolbar(): Toolbar = custom_toolbar as Toolbar
}