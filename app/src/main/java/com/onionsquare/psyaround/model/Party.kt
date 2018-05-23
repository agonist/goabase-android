package com.onionsquare.psyaround.model

import com.google.gson.annotations.SerializedName

data class Parties(
        @SerializedName("partylist")
        val parties: List<Party>
)

data class PartyReply(
        @SerializedName("party")
        val party: Party
)

data class Party(

        @SerializedName("id")
        val id: String,

        @SerializedName("nameParty")
        val nameParty: String,

        @SerializedName("dateStart")
        val dateStart: String,

        @SerializedName("dateEnd")
        val dateEnd: String,

        @SerializedName("nameType")
        val nameType: String,

        @SerializedName("nameCountry")
        val nameCountry: String,

        @SerializedName("isoCountry")
        val isoCountry: String,

        @SerializedName("nameTown")
        val nameTown: String,

        @SerializedName("geoLat")
        val geoLat: String,

        @SerializedName("geoLon")
        val geoLon: String,

        @SerializedName("nameOrganizer")
        val nameOrganizer: String,

        @SerializedName("urlOrganizer")
        val urlOrganizer: String,

        @SerializedName("urlImageSmall")
        val urlImageSmall: String?,

        @SerializedName("urlImageMedium")
        val urlImageMedium: String?,

        @SerializedName("dateCreated")
        val dateCreated: String,

        @SerializedName("dateModified")
        val dateModified: String,

        @SerializedName("nameStatus")
        val nameStatus: String,

        @SerializedName("urlPartyHtml")
        val urlPartyHtml: String,

        @SerializedName("urlPartyJson")
        val urlPartyJson: String
)