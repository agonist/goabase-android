package com.onionsquare.goabase.model

import com.squareup.moshi.Json
import java.io.Serializable

data class Parties(
        @Json(name = "partylist")
        val parties: List<Party>
)

data class PartyDetails(
        @Json(name = "party")
        val party: Party
)

data class Party(

        @Json(name = "id")
        val id: String,

        @Json(name = "nameParty")
        val nameParty: String,

        @Json(name = "dateStart")
        val dateStart: String,

        @Json(name = "dateEnd")
        val dateEnd: String,

        @Json(name = "nameType")
        val nameType: String,

        @Json(name = "nameCountry")
        val nameCountry: String,

        @Json(name = "isoCountry")
        val isoCountry: String,

        @Json(name = "nameTown")
        val nameTown: String,

        @Json(name = "geoLat")
        val geoLat: String?,

        @Json(name = "geoLon")
        val geoLon: String?,

        @Json(name = "nameOrganizer")
        val nameOrganizer: String,

        @Json(name = "urlOrganizer")
        val urlOrganizer: String?,

        @Json(name = "urlImageSmall")
        val urlImageSmall: String?,

        @Json(name = "urlImageMedium")
        val urlImageMedium: String?,

        @Json(name = "urlImageFull")
        val urlImageFull: String?,

        @Json(name = "textLineUp")
        val textLineup: String?,

        @Json(name = "textDeco")
        val textDeco: String?,

        @Json(name = "textLocation")
        val textLocation: String?,

        @Json(name = "textEntryFee")
        val textEntryFee: String?,

        @Json(name = "textMore")
        val textMore: String?

) : Serializable