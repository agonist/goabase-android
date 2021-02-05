package com.onionsquare.goabase.feature.partydetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.util.Linkify
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.onionsquare.goabase.R
import com.onionsquare.goabase.databinding.ActivityPartyDetailsBinding
import com.onionsquare.goabase.feature.parties.PartiesActivity
import com.onionsquare.goabase.gone
import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.visible
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class PartyDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPartyDetailsBinding
    private val viewModel: PartyDetailsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPartyDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val partyId = intent.getStringExtra(PartiesActivity.PARTY_ID_EXTRA)
        binding.backArrow.setOnClickListener { onBackPressed() }


        viewModel.party.observe(this, { handleAction(it) })
        partyId?.let {
            binding.retryButton.setOnClickListener { viewModel.getPartyById(partyId) }
            viewModel.getPartyById(partyId)
        }
    }

    private fun handleAction(action: PartyDetailsAction) {
        when (action) {
            is PartyDetailsAction.Error -> showErrorState()
            is PartyDetailsAction.GetPartyDetailsSuccess -> showPartyDetails(action.party)
            is PartyDetailsAction.Loading -> showLoadingState()
        }
    }

    private fun showLoadingState() {
        hideErrorState()
        binding.detailsContainer.gone()
        binding.detailsProgress.visible()
    }

    private fun hideLoadingState() {
        binding.detailsContainer.visible()
        binding.detailsProgress.gone()
    }

    private fun showErrorState() {
        binding.errorView.visible()
    }

    private fun hideErrorState() {
        binding.errorView.gone()
    }

    private fun showPartyDetails(party: Party) {
        hideLoadingState()
        party.apply {

            urlImageFull?.let {
                binding.partyPicture.load(urlImageFull) {
                    crossfade(true)
                    error(R.drawable.rick)
                }
            } ?: kotlin.run {
                binding.partyPicture.setImageDrawable(getDrawable(R.drawable.rick))
            }

            binding.partyName.text = nameParty

            val dateStart = OffsetDateTime.parse(dateStart).toLocalDateTime()
            val dateEnd = OffsetDateTime.parse(dateEnd).toLocalDateTime()
            binding.partyDate.text = "${DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(dateStart)} - ${DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(dateEnd)}"

            if (textLocation != "Unknnown") {
                binding.partyLocation.text = nameTown
                binding.locationTitle.visibility = View.VISIBLE
                binding.locationFull.visibility = View.VISIBLE
                binding.locationFull.text = textLocation
            } else {
                binding.locationTitle.visibility = View.GONE
                binding.locationFull.visibility = View.GONE
                binding.partyLocation.text = textLocation
            }
            binding.partyLocation.setOnClickListener {
                val gmmIntentUri = Uri.parse("geo:${party.geoLat},${party.geoLon}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.`package` = "com.google.android.apps.maps"
                startActivity(mapIntent)
            }

            if (textLineup.isNotEmpty()) {
                binding.partyLineup.text = textLineup
                Linkify.addLinks(binding.partyLineup, Linkify.EMAIL_ADDRESSES or Linkify.WEB_URLS)
            }

            if (textMore.isNotEmpty()) {
                binding.partyInfo.text = textMore
                Linkify.addLinks(binding.partyInfo, Linkify.EMAIL_ADDRESSES or Linkify.WEB_URLS)
            }

            if (textDeco.isNotEmpty()) {
                binding.partyDeco.text = textDeco
                Linkify.addLinks(binding.partyDeco, Linkify.EMAIL_ADDRESSES or Linkify.WEB_URLS)
            }

            if (textEntryFee.isNotEmpty()) {
                binding.partyFee.text = textEntryFee
            }

            if (nameOrganizer.isNotEmpty()) {
                binding.partyOrganizer.text = "${nameOrganizer}"
            }
            urlOrganizer?.let {
                binding.partyOrganizer.text = "${binding.partyOrganizer.text}\n${urlOrganizer}"
            }
            Linkify.addLinks(binding.partyOrganizer, Linkify.EMAIL_ADDRESSES or Linkify.WEB_URLS)
        }
    }
}