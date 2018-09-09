package com.onionsquare.goabase.model

import com.google.gson.annotations.SerializedName

data class Countries(

        @SerializedName("countrylist")
        val countries: List<Country>

)

data class Country(

        @SerializedName("nameCountry")
        val nameCountry: String,

        @SerializedName("isoCountry")
        val isoCountry: String,

        @SerializedName("numParties")
        val numParties: String,

        @SerializedName("urlCountry")
        val urlCountry: String

)