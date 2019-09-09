package com.onionsquare.goabase.feature.country

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.onionsquare.goabase.R
import com.onionsquare.goabase.feature.parties.PartiesActivity
import com.onionsquare.goabase.model.Country
import com.onionsquare.goabase.ui.LoadingObserver
import kotlinx.android.synthetic.main.countries.*
import org.koin.android.ext.android.inject


class CountriesActivity : AppCompatActivity(), CountryAdapter.CountryClickListener {

    companion object {
        const val COUNTRY_NAME_EXTRA = "COUNTRY_NAME"
        const val COUNTRY_ISO_EXTRA = "COUNTRY_ISO"
    }

    private val viewModel: CountriesViewModel by inject()
    private val adapter: CountryAdapter = CountryAdapter(arrayListOf(), this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.countries)
        setSupportActionBar(custom_toolbar as Toolbar)
        supportActionBar?.title = getString(R.string.countries_title)

        countries_recycler.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        countries_recycler.layoutManager = layoutManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(this, R.color.purpleLight)
        }
        countries_recycler.adapter = adapter
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.countries.observe(this, adapter)
        viewModel.loading.observe(this, LoadingObserver(country_progress, countries_recycler))
    }

    override fun onCountrySelected(country: Country) {
        Intent(this@CountriesActivity, PartiesActivity::class.java).let {
            it.putExtra(COUNTRY_NAME_EXTRA, country.nameCountry)
            it.putExtra(COUNTRY_ISO_EXTRA, country.isoCountry)
            startActivity(it)
        }
    }
}