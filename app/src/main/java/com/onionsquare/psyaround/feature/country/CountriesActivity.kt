package com.onionsquare.psyaround.feature.country

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import com.onionsquare.psyaround.PsyApp
import com.onionsquare.psyaround.R
import com.onionsquare.psyaround.feature.parties.PartiesActivity
import com.onionsquare.psyaround.model.Country
import kotlinx.android.synthetic.main.countries.*

class CountriesActivity : AppCompatActivity(), CountriesView {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.countries)
        (custom_toolbar as Toolbar).setTitleTextColor(ContextCompat.getColor(this, R.color.colorText))
        setSupportActionBar(custom_toolbar as Toolbar)
        countries_recycler.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        countries_recycler.layoutManager = layoutManager
        CountriesPresenter(this, PsyApp.instance.api).init()
    }

    override fun showCountries(countries: List<Country>) {
        val adapter = CountryAdapter(countries, object : CountryAdapter.CountryClickListener{
            override fun onClick(country: Country) {
                val intent = Intent(this@CountriesActivity, PartiesActivity::class.java)
                intent.putExtra("COUNTRY_NAME", country.nameCountry)
                startActivity(intent)
            }
        })
        countries_recycler.adapter = adapter
    }

}