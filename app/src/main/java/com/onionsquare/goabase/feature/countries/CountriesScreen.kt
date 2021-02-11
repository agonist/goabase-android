package com.onionsquare.goabase.feature.countries

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.AmbientContext
import androidx.compose.ui.res.loadImageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.heetch.countrypicker.Utils
import com.onionsquare.goabase.R
import com.onionsquare.goabase.feature.CircularLoader
import com.onionsquare.goabase.feature.RetryView
import com.onionsquare.goabase.feature.SimpleTitleToolbar
import com.onionsquare.goabase.model.Country
import com.onionsquare.goabase.theme.AlienGreen
import com.onionsquare.goabase.theme.GoabaseTheme
import java.util.*


@Composable
fun CountriesScreen(viewModel: CountriesViewModel) {

    val countriesState by viewModel.countries.observeAsState()

    Scaffold(topBar = { SimpleTitleToolbar(AmbientContext.current.getString(R.string.countries_title)) }) {
        Surface {
            BodyContent(countriesState!!, { viewModel.fetchCountries() }, { country -> viewModel.onCountryClicked(country) })
        }
    }
}


@Composable
fun BodyContent(countriesState: CountriesScreenState, onRetryClicked: () -> Unit, onCountryClicked: (Country) -> Unit) {
    when (countriesState) {
        is CountriesScreenState.Loading, CountriesScreenState.Init -> CircularLoader()
        is CountriesScreenState.ListCountriesSuccess -> CountryList(countriesState.countries, onCountryClicked)
        is CountriesScreenState.Error -> RetryView(message = AmbientContext.current.getString(R.string.countries_error_message), onRetryClicked = { onRetryClicked() })
    }
}

@Composable
fun CountryList(countries: List<Country>, onCountryClicked: (Country) -> Unit) {
    LazyColumn {
        itemsIndexed(items = countries) { _, item ->
            CountryItem(item, onCountryClicked)
        }
    }
}

@Composable
fun CountryItem(country: Country, onCountryClicked: (Country) -> Unit) {
    ConstraintLayout(modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.background)
            .clickable { onCountryClicked(country) }
            .padding(16.dp), constraintSet =
    ConstraintSet {
        val flag = createRefFor("flag")
        val countryName = createRefFor("countryName")
        val partyAmount = createRefFor("partyAmount")

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
            end.linkTo(parent.end)
            top.linkTo(flag.top)
            bottom.linkTo(flag.bottom)
        }
    }) {

        val image = loadImageResource(Utils.getMipmapResId(AmbientContext.current, country.isoCountry.toLowerCase(Locale.getDefault()) + "_flag"))
        image.resource.resource?.let {
            Image(bitmap = it, contentDescription = null, Modifier
                    .layoutId("flag")
                    .width(Dp(25f)))
        }

        Text(modifier = Modifier
                .layoutId("countryName")
                .padding(start = 8.dp), text = country.nameCountry, style = MaterialTheme.typography.h6)
        Text(modifier = Modifier
                .layoutId("partyAmount")
                .background(AlienGreen, RoundedCornerShape(8.dp))
                .padding(start = 8.dp, end = 10.dp, bottom = 2.dp),
                style = MaterialTheme.typography.body2,
                text = AmbientContext.current.resources.getQuantityString(R.plurals.numberOfParties, country.numParties.toInt(), country.numParties.toInt()))
    }
}

@Composable
@Preview
fun CountryListPreview() {
    GoabaseTheme {
        CountryList(countries = arrayListOf(
                Country("France", "fr", "10", "xxx"),
                Country("Germany", "de", "5", "xxx"),
        )) {}
    }

}