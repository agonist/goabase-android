package com.onionsquare.goabase.feature.parties

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import com.onionsquare.goabase.databinding.ActivityPartiesBinding
import com.onionsquare.goabase.extraNotNull
import com.onionsquare.goabase.feature.Const.COUNTRY_NAME_EXTRA
import com.onionsquare.goabase.feature.Const.PARTY_ID_EXTRA
import com.onionsquare.goabase.feature.partydetails.PartyDetailsActivity
import com.onionsquare.goabase.gone
import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.visible
import org.koin.androidx.viewmodel.ext.android.viewModel

class PartiesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPartiesBinding
    private val viewModel: PartiesViewModel by viewModel()
    private val adapter: CompositeDelegateAdapter = CompositeDelegateAdapter(
            PartyDelegateAdapter { party -> onPartySelected(party) }
    )
    private val country by extraNotNull<String>(COUNTRY_NAME_EXTRA)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPartiesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.customToolbar.root)
        supportActionBar?.apply {
            title = "Parties in $country"
            setDisplayHomeAsUpEnabled(true)
        }

        binding.partiesRecycler.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this@PartiesActivity)
            adapter = adapter
        }

        binding.retryButton.setOnClickListener { viewModel.fetchParties(country) }

        viewModel.parties.observe(this, { handleAction(it) })
        viewModel.fetchParties(country)
    }

    private fun handleAction(actions: PartiesActions) {
        when (actions) {
            is PartiesActions.Error -> showErrorState()
            is PartiesActions.ListPartiesSuccess -> refreshData(actions.parties)
            is PartiesActions.Loading -> showLoadingState()
        }
    }

    private fun refreshData(parties: List<Party>) {
        hideErrorState()
        hideLoadingState()
        adapter.swapData(parties)
    }

    private fun showLoadingState() {
        hideErrorState()
        binding.partiesRecycler.gone()
        binding.partiesProgress.visible()
    }

    private fun hideLoadingState() {
        binding.partiesRecycler.visible()
        binding.partiesProgress.gone()
    }

    private fun showErrorState() {
        binding.errorView.visible()
    }

    private fun hideErrorState() {
        binding.errorView.gone()
    }

    private fun onPartySelected(party: Party) {
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