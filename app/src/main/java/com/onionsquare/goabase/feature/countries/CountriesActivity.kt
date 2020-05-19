package com.onionsquare.goabase.feature.countries

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.onionsquare.goabase.R
import com.onionsquare.goabase.databinding.ActivityCountriesBinding
import com.onionsquare.goabase.feature.parties.PartiesActivity
import com.onionsquare.goabase.gone
import com.onionsquare.goabase.model.Country
import com.onionsquare.goabase.visible
import kotlinx.android.synthetic.main.activity_countries.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel


@ExperimentalCoroutinesApi
class CountriesActivity : AppCompatActivity(), CountryAdapter.CountryClickListener {

    companion object {
        const val COUNTRY_NAME_EXTRA = "COUNTRY_NAME"
        const val COUNTRY_ISO_EXTRA = "COUNTRY_ISO"
    }


    private val viewModel: CountriesViewModel by viewModel()
    private val adapter: CountryAdapter = CountryAdapter(arrayListOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityCountriesBinding = DataBindingUtil.setContentView(this, R.layout.activity_countries)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        //setContentView(view)

        setSupportActionBar(custom_toolbar as Toolbar)
        supportActionBar?.title = getString(R.string.countries_title)

        countries_recycler.setHasFixedSize(true)
        countries_recycler.layoutManager = LinearLayoutManager(this)
        countries_recycler.adapter = adapter

        observeViewModel()
        viewModel.getCountriesAll()
    }

    private fun observeViewModel() {
        viewModel.countries.observe(this, Observer {
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

    private fun refreshData(countries: List<Country>) {
        hideErrorState()
        hideLoadingState()
        adapter.updateData(countries)
    }

    private fun showLoadingState() {
        hideErrorState()
        countries_recycler.gone()
        country_progress.visible()
    }

    private fun hideLoadingState() {
        countries_recycler.visible()
        country_progress.gone()
    }

    private fun showErrorState() {
        error_view.visible()
    }

    private fun hideErrorState() {
        error_view.gone()
    }

    override fun onCountrySelected(country: Country) {
        Intent(this@CountriesActivity, PartiesActivity::class.java).let {
            it.putExtra(COUNTRY_NAME_EXTRA, country.nameCountry)
            it.putExtra(COUNTRY_ISO_EXTRA, country.isoCountry)
            startActivity(it)
        }
    }
}