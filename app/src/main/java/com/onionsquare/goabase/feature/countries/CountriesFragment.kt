package com.onionsquare.goabase.feature.countries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.onionsquare.goabase.R
import com.onionsquare.goabase.feature.Const
import com.onionsquare.goabase.model.Country
import com.onionsquare.goabase.theme.GoabaseTheme
import org.koin.androidx.viewmodel.ext.android.viewModel


class CountriesFragment : Fragment() {

    private val viewModel: CountriesViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        viewModel.userActions.asLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is CountriesScreenAction.CountryClicked -> onCountrySelected(it.country)
            }
        }

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
        findNavController()
                .navigate(
                        R.id.action_countriesFragment_to_partiesFragment,
                        bundleOf(Pair(Const.COUNTRY_NAME_EXTRA, country.nameCountry))
                )
    }
}