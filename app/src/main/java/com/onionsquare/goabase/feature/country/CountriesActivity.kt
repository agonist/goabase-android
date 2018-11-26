package com.onionsquare.goabase.feature.country

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.DrawerBuilder
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.onionsquare.goabase.PsyApp
import com.onionsquare.goabase.R
import com.onionsquare.goabase.feature.BaseActivity
import com.onionsquare.goabase.feature.about.AboutActivity
import com.onionsquare.goabase.feature.parties.PartiesActivity
import com.onionsquare.goabase.model.Country
import kotlinx.android.synthetic.main.countries.*


class CountriesActivity : BaseActivity(), CountriesView {

    companion object {
        val COUNTRY_NAME_EXTRA = "COUNTRY_NAME"
        val COUNTRY_ISO_EXTRA = "COUNTRY_ISO"
    }

    private lateinit var drawer: Drawer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildDrawer()

        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp)
            title =  getString(R.string.countries_title)
        }

        countries_recycler.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        countries_recycler.layoutManager = layoutManager
        CountriesPresenter(this, PsyApp.instance.api).init()
    }

    override fun showCountries(countries: List<Country>) {
        val adapter = CountryAdapter(countries, object : CountryAdapter.CountryClickListener {
            override fun onClick(country: Country) {
                val intent = Intent(this@CountriesActivity, PartiesActivity::class.java)
                intent.putExtra(COUNTRY_NAME_EXTRA, country.nameCountry)
                intent.putExtra(COUNTRY_ISO_EXTRA, country.isoCountry)
                startActivity(intent)
            }
        })
        countries_recycler.adapter = adapter
    }

    private fun buildDrawer() {
        val item1 = PrimaryDrawerItem().withIdentifier(1).withName("About")


        drawer = DrawerBuilder().withActivity(this)
                .withHeader(layoutInflater.inflate(R.layout.drawer_header, null))
                .addDrawerItems(item1)
                .withOnDrawerItemClickListener { view, position, drawerItem ->
                    when (drawerItem) {
                        item1 -> {
                            startActivity(Intent(this, AboutActivity::class.java))
                            true
                        }
                        else -> {
                            true
                        }
                    }
                }
                .build()

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawer.openDrawer()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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