package com.onionsquare.goabase.feature.countries

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.livermor.delegateadapter.delegate.CompositeDelegateAdapter
import com.onionsquare.goabase.R
import com.onionsquare.goabase.databinding.ActivityCountriesBinding
import com.onionsquare.goabase.feature.parties.PartiesActivity
import com.onionsquare.goabase.gone
import com.onionsquare.goabase.model.Country
import com.onionsquare.goabase.visible
import org.koin.androidx.viewmodel.ext.android.viewModel


class CountriesActivity : AppCompatActivity() {

    companion object {
        const val COUNTRY_NAME_EXTRA = "COUNTRY_NAME"
        const val COUNTRY_ISO_EXTRA = "COUNTRY_ISO"
    }

    private val adapter: CompositeDelegateAdapter = CompositeDelegateAdapter(
            CountryDelegateAdapter { country -> onCountrySelected(country) }
    )

    private lateinit var binding: ActivityCountriesBinding
    private val viewModel: CountriesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCountriesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.customToolbar.root)
        supportActionBar?.title = getString(R.string.countries_title)

        binding.countriesRecycler.setHasFixedSize(true)
        binding.countriesRecycler.layoutManager = LinearLayoutManager(this)
        binding.countriesRecycler.adapter = adapter

        viewModel.countries.observe(this, { handleAction(it) })
        viewModel.fetchCountries()
    }

    private fun handleAction(action: CountriesActions) {
        when (action) {
            is CountriesActions.Error -> showErrorState()
            is CountriesActions.ListCountriesSuccess -> refreshData(action.countries)
            is CountriesActions.Loading -> showLoadingState()
        }
    }

    private fun refreshData(countries: List<Country>) {
        hideErrorState()
        hideLoadingState()
        adapter.swapData(countries)
    }

    private fun showLoadingState() {
        hideErrorState()
        binding.countriesRecycler.gone()
        binding.countryProgress.visible()
    }

    private fun hideLoadingState() {
        binding.countriesRecycler.visible()
        binding.countryProgress.gone()
    }

    private fun showErrorState() {
        binding.errorView.visible()
    }

    private fun hideErrorState() {
        binding.errorView.gone()
    }

    private fun onCountrySelected(country: Country) {
        Intent(this@CountriesActivity, PartiesActivity::class.java).apply {
            putExtra(COUNTRY_NAME_EXTRA, country.nameCountry)
            putExtra(COUNTRY_ISO_EXTRA, country.isoCountry)
            startActivity(this)
        }
    }
}