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
import com.onionsquare.goabase.gone
import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.visible
import kotlinx.android.synthetic.main.activity_party_details.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle


@ExperimentalCoroutinesApi
class PartyDetailsActivity : AppCompatActivity(R.layout.activity_party_details) {

    private val viewModel: PartyDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val partyId = intent.getStringExtra(PartiesActivity.PARTY_ID_EXTRA)
        back_Arrow.setOnClickListener { onBackPressed() }
        retry_button.setOnClickListener { viewModel.getPartyById(partyId) }
        observeViewModel()
        viewModel.getPartyById(partyId)
    }


    private fun observeViewModel() {
        viewModel.party.observe(this, Observer {
            showPartyDetails(it)
        })

        viewModel.loading.observe(this, Observer {
            when (it) {
                true -> showLoadingState()
                false -> hideLoadingState()
            }
        })

        viewModel.error.observe(this, Observer {
            showErrorState()
        })
    }

    private fun showLoadingState() {
        hideErrorState()
        details_container.gone()
        details_progress.visible()
    }

    private fun hideLoadingState() {
        details_container.visible()
        details_progress.gone()
    }

    private fun showErrorState() {
        error_view.visible()
    }

    private fun hideErrorState() {
        error_view.gone()
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