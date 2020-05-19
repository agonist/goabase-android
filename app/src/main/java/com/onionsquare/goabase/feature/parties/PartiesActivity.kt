package com.onionsquare.goabase.feature.parties

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.onionsquare.goabase.R
import com.onionsquare.goabase.feature.countries.CountriesActivity
import com.onionsquare.goabase.feature.partydetails.PartyDetailsActivity
import com.onionsquare.goabase.gone
import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.visible
import kotlinx.android.synthetic.main.activity_parties.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel

@ExperimentalCoroutinesApi
class PartiesActivity : AppCompatActivity(R.layout.activity_parties), PartiesAdapter.PartyClickListener {

    companion object {
        const val PARTY_ID_EXTRA = "PARTY_ID"
    }

    private val viewModel: PartiesViewModel by viewModel()
    private val adapter = PartiesAdapter(arrayListOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val country = intent.getStringExtra(CountriesActivity.COUNTRY_NAME_EXTRA)

        setSupportActionBar(custom_toolbar as Toolbar)
        supportActionBar?.apply {
            title = "Parties in $country"
            setDisplayHomeAsUpEnabled(true)
        }

        parties_recycler.setHasFixedSize(true)
        parties_recycler.layoutManager = LinearLayoutManager(this)
        parties_recycler.adapter = adapter

        retry_button.setOnClickListener { viewModel.getPartiesByCountry(country!!) }

        observeViewModel()

        country?.let {
            viewModel.getPartiesByCountry(country)
        }
    }

    private fun observeViewModel() {
        viewModel.parties.observe(this, Observer {
            refreshData(it)
        })

        viewModel.loading.observe(this, Observer {
            when (it) {
                true -> showLoadingState()
                false -> hideLoadingState()
            }
        })

        viewModel.error.observe(this, Observer {
            showErrorState()
        })
    }

    private fun refreshData(parties: List<Party>) {
        hideErrorState()
        hideLoadingState()
        adapter.refreshDate(parties)
    }

    private fun showLoadingState() {
        hideErrorState()
        parties_recycler.gone()
        parties_progress.visible()
    }

    private fun hideLoadingState() {
        parties_recycler.visible()
        parties_progress.gone()
    }

    private fun showErrorState() {
        error_view.visible()
    }

    private fun hideErrorState() {
        error_view.gone()
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