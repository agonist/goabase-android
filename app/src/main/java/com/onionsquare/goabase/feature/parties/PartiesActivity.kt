package com.onionsquare.goabase.feature.parties

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.onionsquare.goabase.R
import com.onionsquare.goabase.feature.Divider
import com.onionsquare.goabase.feature.country.CountriesActivity
import com.onionsquare.goabase.feature.partydetails.PartyDetailsActivity
import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.ui.LoadingObserver
import kotlinx.android.synthetic.main.parties.*
import org.koin.android.ext.android.inject

class PartiesActivity : AppCompatActivity(), PartiesAdapter.PartyClickListener {

    companion object {
        const val PARTY_ID_EXTRA = "PARTY_ID"
    }

    private val viewModel: PartiesViewModel by inject()
    private val adapter = PartiesAdapter(arrayListOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.parties)
        initUI()

        val country = intent.getStringExtra(CountriesActivity.COUNTRY_NAME_EXTRA)

        viewModel.parties.observe(this, adapter)
        viewModel.loading.observe(this, LoadingObserver(parties_progress, parties_recycler))

        viewModel.fetchParties(country)
    }

    private fun initUI() {
        val country = intent.getStringExtra(CountriesActivity.COUNTRY_NAME_EXTRA)
        setSupportActionBar(custom_toolbar as Toolbar)
        supportActionBar?.title = "Parties in $country"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        parties_recycler.setHasFixedSize(true)
        parties_recycler.layoutManager = LinearLayoutManager(this)
        parties_recycler.adapter = adapter
        parties_recycler.addItemDecoration(Divider(applicationContext))
    }

    override fun onPartySelected(party: Party) {
        val intent = Intent(this@PartiesActivity, PartyDetailsActivity::class.java)
        intent.putExtra(PARTY_ID_EXTRA, party.id)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}