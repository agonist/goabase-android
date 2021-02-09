package com.onionsquare.goabase.model

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
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

//data class CountryItem(
//        @DrawableRes
//        val res: Int,
//
//        @SerializedName("nameCountry")
//        val nameCountry: String,
//
//        @SerializedName("isoCountry")
//        val isoCountry: String,
//
//        @SerializedName("numParties")
//        val numParties: String,
//
//        @SerializedName("urlCountry")
//        val urlCountry: String
//
//)