package com.onionsquare.goabase.feature.parties

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.onionsquare.goabase.R
import com.onionsquare.goabase.feature.CircularLoader
import com.onionsquare.goabase.feature.FormattedDateText
import com.onionsquare.goabase.feature.RetryView
import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.theme.Mustard
import dev.chrisbanes.accompanist.coil.CoilImage


@Composable
fun PartiesScreen(viewModel: PartiesViewModel, countryName: String) {

    val partiesState by viewModel.parties.observeAsState(PartiesScreenState.Loading)

    Scaffold(topBar = {
        TopAppBar(
                backgroundColor = MaterialTheme.colors.secondary,
                title = {
                    Text(text = AmbientContext.current.getString(R.string.parties_title, countryName))
                },
                elevation = 0.dp,
                contentColor = Mustard,
                navigationIcon = { IconButton(onClick = { viewModel.navigateUp() }) {
                    Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null
                    )
                }}
        )

    }) {
        Surface {
            BodyContent(partiesState, { viewModel.fetchParties() }, { party -> viewModel.onPartyClicked(party) })
        }
    }
}


@Composable
fun BodyContent(partiesState: PartiesScreenState, onRetryClicked: () -> Unit, onPartyClicked: (Party) -> Unit) {
    when (partiesState) {
        is PartiesScreenState.Loading, PartiesScreenState.Init -> CircularLoader()
        is PartiesScreenState.Error -> RetryView(message = AmbientContext.current.getString(R.string.parties_error_message), onRetryClicked = { onRetryClicked() })
        is PartiesScreenState.ListPartiesSuccess -> PartiesList(parties = partiesState.parties, onPartyClicked)
    }
}

@Composable
fun PartiesList(parties: List<Party>, onPartyClicked: (Party) -> Unit) {
    LazyColumn {
        itemsIndexed(items = parties) { _, item ->
            PartyItem(party = item, onPartyClicked)
        }
    }
}

@Composable
fun PartyItem(party: Party, onPartyClicked: (Party) -> Unit) {
    Row(modifier =
    Modifier
            .fillMaxWidth()
            .clickable { onPartyClicked(party) }
            .background(MaterialTheme.colors.background)
            .padding(16.dp)

    ) {
        PartyPic(party = party)
        Column(modifier = Modifier.padding(start = 16.dp)) {
            FormattedDateText(date = party.dateStart)
            Text(text = party.nameParty, style = MaterialTheme.typography.h5)
            Row {
                Icon(vectorResource(id = R.drawable.ic_location_on_black_24dp), contentDescription = null, tint = Mustard, modifier = Modifier.height(20.dp))
                Text(text = party.nameTown)
            }
        }
    }
}

@Composable
fun PartyPic(
        party: Party
) {
    Box(Modifier
            .width(80.dp)
            .height(120.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        party.urlImageMedium?.let {
            CoilImage(
                    data = party.urlImageMedium, null, contentScale = ContentScale.Crop)
        } ?: run {
            Image(bitmap = imageResource(id = R.drawable.no_picture), contentDescription = null, contentScale = ContentScale.Crop, modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight())
        }
    }
}