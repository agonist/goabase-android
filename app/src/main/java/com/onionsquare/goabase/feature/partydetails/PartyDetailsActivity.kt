package com.onionsquare.goabase.feature.partydetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.util.Linkify
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import coil.api.load
import com.onionsquare.goabase.R
import com.onionsquare.goabase.feature.parties.PartiesActivity
import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.ui.LoadingObserver
import kotlinx.android.synthetic.main.party_details.*
import org.koin.android.ext.android.inject
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle


class PartyDetailsActivity : AppCompatActivity() {

    private val viewModel: PartyDetailsViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.party_details)
        val partyId = intent.getStringExtra(PartiesActivity.PARTY_ID_EXTRA)
        back_Arrow.setOnClickListener { onBackPressed() }

        viewModel.loading.observe(this, LoadingObserver(details_progress, details_container))

        viewModel.party.observe(this, Observer {
            showPartyDetails(it)
        })

        viewModel.setPartyId(partyId)
    }


    private fun showPartyDetails(party: Party) {
        party.apply {

            urlImageFull?.let {
                party_picture.load(urlImageFull) {
                    crossfade(true)
                    error(R.drawable.rick)
                }
            } ?: kotlin.run {
                party_picture.setImageDrawable(getDrawable(R.drawable.rick))
            }

            party_name.text = nameParty

            val dateStart = OffsetDateTime.parse(dateStart).toLocalDateTime()
            val dateEnd = OffsetDateTime.parse(dateEnd).toLocalDateTime()
            party_date.text = "${DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(dateStart)} - ${DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(dateEnd)}"

            if (textLocation != "Unknnown") {
                party_location.text = nameTown
                location_title.visibility = View.VISIBLE
                location_full.visibility = View.VISIBLE
                location_full.text = textLocation
            } else {
                location_title.visibility = View.GONE
                location_full.visibility = View.GONE
                party_location.text = textLocation
            }
            party_location.setOnClickListener {
                val gmmIntentUri = Uri.parse("geo:${party.geoLat},${party.geoLon}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.`package` = "com.google.android.apps.maps"
                startActivity(mapIntent)
            }

            if (textLineup.isNotEmpty()) {
                party_lineup.text = textLineup
                Linkify.addLinks(party_lineup, Linkify.EMAIL_ADDRESSES or Linkify.WEB_URLS)
            }

            if (textMore.isNotEmpty()) {
                party_info.text = textMore
                Linkify.addLinks(party_info, Linkify.EMAIL_ADDRESSES or Linkify.WEB_URLS)
            }

            if (textDeco.isNotEmpty()) {
                party_deco.text = textDeco
                Linkify.addLinks(party_deco, Linkify.EMAIL_ADDRESSES or Linkify.WEB_URLS)
            }

            if (textEntryFee.isNotEmpty()) {
                party_fee.text = textEntryFee
            }

            if (nameOrganizer.isNotEmpty()) {
                party_organizer.text = "${nameOrganizer}"
            }
            urlOrganizer?.let {
                party_organizer.text = "${party_organizer.text}\n${urlOrganizer}"
            }
            Linkify.addLinks(party_organizer, Linkify.EMAIL_ADDRESSES or Linkify.WEB_URLS)
        }
    }
}