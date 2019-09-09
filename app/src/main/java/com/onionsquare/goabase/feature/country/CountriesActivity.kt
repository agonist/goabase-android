package com.onionsquare.goabase.feature.country

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.onionsquare.goabase.R
import com.onionsquare.goabase.feature.BaseActivity
import com.onionsquare.goabase.feature.parties.PartiesActivity
import com.onionsquare.goabase.model.Country
import kotlinx.android.synthetic.main.countries.*
import org.koin.android.ext.android.inject


class CountriesActivity : BaseActivity() {

    companion object {
        const val COUNTRY_NAME_EXTRA = "COUNTRY_NAME"
        const val COUNTRY_ISO_EXTRA = "COUNTRY_ISO"
    }

    private val viewModel: CountriesViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initUI()

        viewModel.countries.observe(this, Observer { res ->
            showCountries(res)
        })

        viewModel.loading.observe(this, Observer { isLoading ->
            when (isLoading) {
                false -> hideLoader()
                true -> showLoader()
            }
        })
    }

    private fun initUI() {
        supportActionBar?.apply {
            title = getString(R.string.countries_title)
        }

        countries_recycler.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        countries_recycler.layoutManager = layoutManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.purpleLight)
        }
    }

    private fun showCountries(countries: List<Country>) {
        val adapter = CountryAdapter(countries, object : CountryAdapter.CountryClickListener {
            override fun onClick(country: Country) {
                Intent(this@CountriesActivity, PartiesActivity::class.java).let {
                    it.putExtra(COUNTRY_NAME_EXTRA, country.nameCountry)
                    it.putExtra(COUNTRY_ISO_EXTRA, country.isoCountry)
                    startActivity(it)
                }
            }
        })
        countries_recycler.adapter = adapter
    }


    private fun showLoader() {
        country_progress.visibility = View.VISIBLE
        countries_recycler.visibility = View.GONE
    }

    private fun hideLoader() {
        country_progress.visibility = View.GONE
        countries_recycler.visibility = View.VISIBLE
    }

    override fun provideLayout(): Int = R.layout.countries

    override fun provideToolbar(): Toolbar = custom_toolbar as Toolbar
}