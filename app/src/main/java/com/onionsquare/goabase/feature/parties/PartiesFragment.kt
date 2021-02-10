package com.onionsquare.goabase.feature.parties

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
import com.onionsquare.goabase.extraNotNull
import com.onionsquare.goabase.feature.Const
import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.theme.GoabaseTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class PartiesFragment : Fragment() {

    private val viewModel: PartiesViewModel by viewModel()
    private val countryName by extraNotNull<String>(Const.COUNTRY_NAME_EXTRA)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        viewModel.userAction.asLiveData().observe(viewLifecycleOwner) {
            when (it) {
                is PartiesScreenActions.PartyClicked -> onPartySelected(it.party)
            }
        }

        viewModel.setCountry(countryName)

        return ComposeView(requireContext()).apply {
            setContent {
                GoabaseTheme {
                    PartiesScreen(viewModel, countryName)
                }
            }
        }
    }

    private fun onPartySelected(party: Party) {
        findNavController()
                .navigate(
                        R.id.action_partiesFragment_to_partyDetailsFragment,
                        bundleOf(Pair(Const.PARTY_ID_EXTRA, party.id))
                )
    }
}