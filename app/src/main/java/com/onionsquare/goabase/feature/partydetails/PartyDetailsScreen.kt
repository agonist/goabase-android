package com.onionsquare.goabase.feature.partydetails

import android.content.Intent
import android.net.Uri
import android.text.SpannableString
import android.text.style.ClickableSpan
import android.view.View
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.gesture.tapGestureFilter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.platform.AmbientUriHandler
import androidx.compose.ui.platform.UriHandlerAmbient
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.annotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import java.util.regex.Pattern

@Composable
fun PartyDetailsScreen(viewModel: PartyDetailsViewModel) {

    val partyDetailsState by viewModel.partyDetails.observeAsState()
    ProvideWindowInsets {
        Scaffold {
            Surface {
                BodyContent(partyDetailsState!!, { viewModel.fetchPartyDetails() }) { viewModel.navigateUp() }
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
fun DetailsComponent(sectionTitle: String, sectionContent: String?) {
    var content = sectionContent
    if (sectionContent.isNullOrEmpty())
        content = "Unknown"

    Column(Modifier.background(PurpleDark)) {
        Text(text = sectionTitle, Modifier
                .fillMaxWidth()
                .background(PurpleLight)
                .padding(start = 16.dp, top = 8.dp, bottom = 8.dp), style = MaterialTheme.typography.subtitle1)
        LinkifyText(text = content!!, Modifier.padding(16.dp))
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

@Composable
fun LinkifyText(text: String, modifier: Modifier = Modifier) {
    val uriHandler = AmbientUriHandler.current
    val layoutResult = remember {
        mutableStateOf<TextLayoutResult?>(null)
    }
    val linksList = extractUrls(text)
    val annotatedString = buildAnnotatedString {
        append(text)
        linksList.forEach {
            addStyle(
                    style = SpanStyle(
                            color = Color.Companion.Blue,
                            textDecoration = TextDecoration.Underline
                    ),
                    start = it.start,
                    end = it.end
            )
            addStringAnnotation(
                    tag = "URL",
                    annotation = it.url,
                    start = it.start,
                    end = it.end
            )
        }
    }
    Text(text = annotatedString, style = MaterialTheme.typography.body1, modifier = modifier.tapGestureFilter { offsetPosition ->
        layoutResult.value?.let {
            val position = it.getOffsetForPosition(offsetPosition)
            annotatedString.getStringAnnotations(position, position).firstOrNull()
                    ?.let { result ->
                        if (result.tag == "URL") {
                            uriHandler.openUri(result.item)
                        }
                    }
        }
    }, onTextLayout = { layoutResult.value = it })
}

private val urlPattern: Pattern = Pattern.compile(
        "(?:^|[\\W])((ht|f)tp(s?):\\/\\/|www\\.)"
                + "(([\\w\\-]+\\.){1,}?([\\w\\-.~]+\\/?)*"
                + "[\\p{Alnum}.,%_=?&#\\-+()\\[\\]\\*$~@!:/{};']*)",
        Pattern.CASE_INSENSITIVE or Pattern.MULTILINE or Pattern.DOTALL
)

fun extractUrls(text: String): List<LinkInfos> {
    val matcher = urlPattern.matcher(text)
    val links = arrayListOf<LinkInfos>()

    while (matcher.find()) {
        val matchStart = matcher.start(1)
        val matchEnd = matcher.end()

        var url = text.substring(matchStart, matchEnd)
        if (!url.startsWith("http://") && !url.startsWith("https://"))
            url = "https://$url"

        links.add(LinkInfos(url, matchStart, matchEnd))
    }
    return links
}

data class LinkInfos(
        val url: String,
        val start: Int,
        val end: Int
)

