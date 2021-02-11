package com.onionsquare.goabase.feature.partydetails

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.onionsquare.goabase.theme.PurpleDark
import com.onionsquare.goabase.theme.PurpleLight
import dev.chrisbanes.accompanist.coil.CoilImage
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import dev.chrisbanes.accompanist.insets.statusBarsPadding

@Composable
fun PartyDetailsScreen(viewModel: PartyDetailsViewModel) {

    val partyDetailsState by viewModel.partyDetails.observeAsState()
    ProvideWindowInsets {
        Scaffold {
            Surface {
                BodyContent(partyDetailsState!!, { viewModel.forceRefresh() }) { viewModel.navigateUp() }
            }
        }
    }
}


@Composable
fun BodyContent(partyDetailsState: PartyDetailsState, onRetryClicked: () -> Unit, onBackPressed: () -> Unit) {
    when (partyDetailsState) {
        is PartyDetailsState.Loading -> CircularLoader()
        is PartyDetailsState.Error -> RetryView(message = "Impossible to get details for now", onRetryClicked = { onRetryClicked() })
        is PartyDetailsState.GetPartyDetailsSuccess -> PartyDetails(party = partyDetailsState.party, onBackPressed)
    }
}

@Composable
fun PartyDetails(party: Party, onBackPressed: () -> Unit) {
    Column(Modifier.verticalScroll(enabled = true, state = rememberScrollState())) {
        PartyPictureHeader(partyUrl = party.urlImageFull) { onBackPressed() }
        PartyHeader(party = party)
        DetailsComponent(AmbientContext.current.getString(R.string.party_details_line_up), party.textLineup)
        DetailsComponent(AmbientContext.current.getString(R.string.party_details_infos), party.textMore)
        DetailsComponent(AmbientContext.current.getString(R.string.party_details_deco), party.textDeco)
        DetailsComponent(AmbientContext.current.getString(R.string.party_details_fee), party.textEntryFee)
        DetailsComponent(AmbientContext.current.getString(R.string.party_details_organizer), party.nameOrganizer)
        DetailsComponent(AmbientContext.current.getString(R.string.party_details_location), party.nameTown)
    }
}

@Composable
fun PartyHeader(party: Party) {
    Column(Modifier
            .fillMaxWidth()
            .background(PurpleDark)
            .padding(16.dp)) {
        Text(text = party.nameParty, style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Icon(vectorResource(id = R.drawable.ic_baseline_calendar_today_24), contentDescription = null, tint = Mustard, modifier = Modifier
                    .height(20.dp)
                    .padding(end = 8.dp))
            FormattedDateText(date = party.dateStart)
            Text(text = " - ")
            FormattedDateText(date = party.dateEnd)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Icon(vectorResource(id = R.drawable.ic_location_on_black_24dp), contentDescription = null, tint = Mustard, modifier = Modifier
                    .height(20.dp)
                    .padding(end = 8.dp))
            Text(text = party.nameTown)
        }
    }
}

@Composable
fun DetailsComponent(sectionTitle: String, sectionContent: String) {
    var content = sectionContent
    if (sectionContent.isEmpty())
        content = "Unknown"

    Column(Modifier.background(PurpleDark)) {
        Text(text = sectionTitle, Modifier
                .fillMaxWidth()
                .background(PurpleLight)
                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp), style = MaterialTheme.typography.subtitle1)
        Text(text = content, Modifier.padding(16.dp), style = MaterialTheme.typography.body1)
    }
}

@Composable
private fun PartyPictureHeader(
        partyUrl: String?,
        upPress: () -> Unit
) {
    Box {
        NetworkImage(
                url = partyUrl,
                contentDescription = null,
                modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(4f / 3f)
        )
        TopAppBar(
                backgroundColor = Color.Transparent,
                elevation = 0.dp,
                contentColor = Mustard,
                modifier = Modifier.statusBarsPadding()
        ) {
            IconButton(onClick = upPress) {
                Icon(
                        imageVector = Icons.Rounded.ArrowBack,
                        contentDescription = null
                )
            }
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}

@Composable
fun NetworkImage(
        url: String?,
        contentDescription: String?,
        modifier: Modifier = Modifier,
        contentScale: ContentScale = ContentScale.Crop,
        placeholderColor: Color? = PurpleDark
) {
    if (url == null)
        Image(bitmap = imageResource(id = R.drawable.rick), contentDescription = "", contentScale = ContentScale.FillWidth, modifier = Modifier
                .fillMaxWidth())
    else
        CoilImage(
                data = url,
                modifier = modifier,
                contentDescription = contentDescription,
                contentScale = contentScale,
                error = {

                },
                loading = {
                    if (placeholderColor != null) {
                        Spacer(
                                modifier = Modifier
                                        .fillMaxSize()
                                        .background(placeholderColor)
                        )
                    }
                }
        )
}

