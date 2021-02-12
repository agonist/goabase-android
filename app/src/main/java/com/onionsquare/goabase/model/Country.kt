package com.onionsquare.goabase.model

import com.squareup.moshi.Json

data class Countries(

        @Json(name = "countrylist")
        val countries: List<Country>

)

data class Country(

        @Json(name = "nameCountry")
        val nameCountry: String,

        @Json(name = "isoCountry")
        val isoCountry: String,

        @Json(name = "numParties")
        val numParties: String,

        @Json(name = "urlCountry")
        val urlCountry: String

)
