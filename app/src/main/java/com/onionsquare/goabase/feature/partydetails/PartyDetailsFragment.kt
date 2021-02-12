package com.onionsquare.goabase.feature.partydetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.onionsquare.goabase.extraNotNull
import com.onionsquare.goabase.feature.Const
import com.onionsquare.goabase.theme.GoabaseTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class PartyDetailsFragment : Fragment() {

    private val viewModel: PartyDetailsViewModel by viewModel()
    private val partyId by extraNotNull<String>(Const.PARTY_ID_EXTRA)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        viewModel.userActions.asLiveData().observe(viewLifecycleOwner) {
            when (it) {
                PartyDetailsActions.NavigateUp -> findNavController().popBackStack()
            }
        }

        viewModel.setPartyId(partyId)

        return ComposeView(requireContext()).apply {
            setContent {
                GoabaseTheme {
                    PartyDetailsScreen(viewModel)
                }
            }
        }
    }
}