package com.onionsquare.goabase.feature.partydetails

import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.Resource
import com.onionsquare.goabase.feature.CircularLoader
import com.onionsquare.goabase.feature.RetryView
import com.onionsquare.goabase.feature.SimpleTitleToolbar
import com.onionsquare.goabase.model.Party

@Composable
fun PartyDetailsScreen(viewModel: PartyDetailsViewModel, partyId: String) {

    val partyDetailsState by viewModel.party.observeAsState()


    Scaffold(topBar = { SimpleTitleToolbar("Party") }) {
        Surface {
            BodyContent(partyDetailsState!!) { viewModel.getPartyById(partyId) }
        }
    }

}


@Composable
fun BodyContent(partyDetailsState: PartyDetailsAction, onRetryClicked: () -> Unit) {
    when (partyDetailsState) {
        is PartyDetailsAction.Loading -> CircularLoader()
        is PartyDetailsAction.Error -> RetryView(message = "Impossible to get details for now", onRetryClicked = { onRetryClicked() })
        is PartyDetailsAction.GetPartyDetailsSuccess -> PartyDetails(party = partyDetailsState.party)
    }
}

@Composable
fun PartyDetails(party: Party) {

}

