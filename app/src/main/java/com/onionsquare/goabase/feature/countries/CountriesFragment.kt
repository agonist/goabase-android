package com.onionsquare.goabase.feature.countries

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import com.onionsquare.goabase.feature.Const
import com.onionsquare.goabase.feature.parties.PartiesActivity
import com.onionsquare.goabase.model.Country
import com.onionsquare.goabase.theme.GoabaseTheme
import org.koin.androidx.viewmodel.ext.android.viewModel


class CountriesFragment : Fragment() {

    private val viewModel: CountriesViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        viewModel.fetchCountries()
        return ComposeView(requireContext()).apply {
            setContent {
                GoabaseTheme {
                    CountriesScreen(viewModel)
                }
            }
        }
    }

    private fun onCountrySelected(country: Country) {
        Intent(requireContext(), PartiesActivity::class.java).apply {
            putExtra(Const.COUNTRY_NAME_EXTRA, country.nameCountry)
            putExtra(Const.COUNTRY_ISO_EXTRA, country.isoCountry)
            startActivity(this)
        }
    }
}