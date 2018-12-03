package com.onionsquare.goabase.feature.country

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import android.view.WindowManager
import com.onionsquare.goabase.PsyApp
import com.onionsquare.goabase.R
import com.onionsquare.goabase.feature.BaseActivity
import com.onionsquare.goabase.feature.parties.PartiesActivity
import com.onionsquare.goabase.model.Country
import kotlinx.android.synthetic.main.countries.*


class CountriesActivity : BaseActivity(), CountriesView {

    companion object {
        val COUNTRY_NAME_EXTRA = "COUNTRY_NAME"
        val COUNTRY_ISO_EXTRA = "COUNTRY_ISO"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.apply {
            title = getString(R.string.countries_title)
        }

        countries_recycler.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        countries_recycler.layoutManager = layoutManager
        CountriesPresenter(this, PsyApp.instance.api).init()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.purpleLight)
        }
    }

    override fun showCountries(countries: List<Country>) {
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


    override fun showLoader() {
        country_progress.visibility = View.VISIBLE
        countries_recycler.visibility = View.GONE
    }

    override fun hideLoader() {
        country_progress.visibility = View.GONE
        countries_recycler.visibility = View.VISIBLE
    }

    override fun provideLayout(): Int = R.layout.countries

    override fun provideToolbar(): Toolbar = custom_toolbar as Toolbar
}