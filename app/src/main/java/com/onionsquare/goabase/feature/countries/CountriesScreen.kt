package com.onionsquare.goabase.feature.countries

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.loadImageResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.heetch.countrypicker.Utils
import com.onionsquare.goabase.R
import com.onionsquare.goabase.model.Country
import com.onionsquare.goabase.theme.AlienGreen
import com.onionsquare.goabase.theme.GoabaseTheme
import com.onionsquare.goabase.theme.Mustard
import com.onionsquare.goabase.theme.PurpleDark


@Composable
fun CountriesScreen(viewModel: CountriesViewModel) {

    val countriesState by viewModel.countries.observeAsState()

    Scaffold(topBar = { Toolbar() }) {
        Surface {
            BodyContent(countriesState!!, { viewModel.fetchCountries()}, {})
        }
    }
}


@Composable
fun BodyContent(countriesState: CountriesActions, onRetryClicked: () -> Unit, onCountryClicked: (Country) -> Unit) {
    when (countriesState) {
        is CountriesActions.Loading -> {
            CircularLoader()
        }
        is CountriesActions.ListCountriesSuccess -> {
            CountryList(countriesState.countries)
        }
        is CountriesActions.Error -> {
            RetryView(message = "Impossible to get countries list for now", onRetryClicked = { onRetryClicked() })
        }
    }
}

@Composable
fun CountryList(countries: List<Country>) {
    LazyColumn {
        itemsIndexed(items = countries) { index, item ->
            CountryItem(item)
        }
    }
}

@Composable
fun CountryItem(country: Country) {
    ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .padding(16.dp), constraintSet =
    ConstraintSet {
        val flag = createRefFor("flag")
        val countryName = createRefFor("countryName")
        val partyAmount = createRefFor("partyAmount")
        val arrow = createRefFor("arrow")

        constrain(flag) {
            start.linkTo(parent.start)
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
        }
        constrain(countryName) {
            start.linkTo(flag.end)
            top.linkTo(flag.top)
            bottom.linkTo(flag.bottom)
        }
        constrain(partyAmount) {
            end.linkTo(arrow.start)
            top.linkTo(flag.top)
            bottom.linkTo(flag.bottom)
        }
        constrain(arrow) {
            end.linkTo(parent.end)
            top.linkTo(flag.top)
            bottom.linkTo(flag.bottom)
        }
    }) {

        val image = loadImageResource(Utils.getMipmapResId(AmbientContext.current, country.isoCountry.toLowerCase() + "_flag"))
        image.resource.resource?.let {
            Image(bitmap = it, contentDescription = " ", Modifier
                    .layoutId("flag")
                    .width(Dp(25f)))
        }

        Text(modifier = Modifier
                .layoutId("countryName")
                .padding(start = 8.dp), text = country.nameCountry, style = MaterialTheme.typography.body1)
        Text(modifier = Modifier
                .layoutId("partyAmount")
                .background(AlienGreen, RoundedCornerShape(8.dp))
                .padding(start = 8.dp, end = 8.dp),
                style = MaterialTheme.typography.body2,
                text = AmbientContext.current.resources.getQuantityString(R.plurals.numberOfParties, country.numParties.toInt(), country.numParties.toInt()))
        Icon(vectorResource(id = R.drawable.ic_chevron_right_black_24dp), contentDescription = null, modifier = Modifier.layoutId("arrow"), tint = Color.Gray)

    }
}

@Composable
fun CircularLoader() {
    Box(
            modifier = Modifier
                    .fillMaxSize()
                    .background(PurpleDark),
    ) {
        CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center), color = Mustard
        )
    }
}

@Composable
fun Toolbar() {
    TopAppBar(
            backgroundColor = MaterialTheme.colors.secondary,
            title = {
                Text(text = "Countries")
            }
    )
}

@Composable
fun RetryView(
        modifier: Modifier = Modifier,
        message: String,
        onRetryClicked: () -> Unit
) {
    Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
    ) {
        Text(
                text = message,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(
                        top = 15.dp,
                        bottom = 20.dp
                )
        )

        Button(onClick = { onRetryClicked() }) {
            Text(text = "retry")
        }
    }
}

@Composable
@Preview
fun CountryListPreview() {
    GoabaseTheme {
        CountryList(countries = arrayListOf(
                Country("France", "fr", "10", "xxx"),
                Country("Germany", "de", "5", "xxx"),
        ))
    }

}