package com.onionsquare.goabase.feature.country

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.View
import com.onionsquare.goabase.PsyApp
import com.onionsquare.goabase.R
import com.onionsquare.goabase.feature.BaseActivity
import com.onionsquare.goabase.feature.HeaderViewDecoration
import com.onionsquare.goabase.feature.parties.PartiesActivity
import com.onionsquare.goabase.model.Country
import kotlinx.android.synthetic.main.countries.*
import kotlinx.android.synthetic.main.header.view.*

class CountriesActivity : BaseActivity(), CountriesView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        countries_recycler.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        countries_recycler.layoutManager = layoutManager
        CountriesPresenter(this, PsyApp.instance.api).init()
    }

    override fun showCountries(countries: List<Country>) {
        val view = layoutInflater.inflate(R.layout.header, null)
        view.name_header.text = getString(R.string.countries_title)
        view.flag_header.visibility = View.GONE
        countries_recycler.addItemDecoration(HeaderViewDecoration(view))

        val adapter = CountryAdapter(countries, object : CountryAdapter.CountryClickListener {
            override fun onClick(country: Country) {
                val intent = Intent(this@CountriesActivity, PartiesActivity::class.java)
                intent.putExtra("COUNTRY_NAME", country.nameCountry)
                intent.putExtra("COUNTRY_ISO", country.isoCountry)
                startActivity(intent)
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


    override fun provideToolbarTitle(): String = ""

    override fun provideLayout(): Int = R.layout.countries

    override fun provideToolbar(): Toolbar = custom_toolbar as Toolbar
}