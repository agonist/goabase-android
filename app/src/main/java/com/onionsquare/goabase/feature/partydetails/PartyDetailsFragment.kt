package com.onionsquare.goabase.feature.partydetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import com.onionsquare.goabase.extraNotNull
import com.onionsquare.goabase.feature.Const
import com.onionsquare.goabase.theme.GoabaseTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class PartyDetailsFragment : Fragment() {

    private val viewModel: PartyDetailsViewModel by viewModel()
    private val partyId by extraNotNull<String>(Const.PARTY_ID_EXTRA)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        viewModel.userActions.asLiveData().observe(viewLifecycleOwner) {
            when (it) {
                PartyDetailsActions.NavigateUp -> findNavController().popBackStack()
            }
        }

        viewModel.setPartyId(partyId)

        return ComposeView(requireContext()).apply {
            setContent {
                GoabaseTheme {
                    PartyDetailsScreen(viewModel)
                }
            }
        }
    }
//
//    private fun showPartyDetails(party: Party) {
//        hideLoadingState()
//        party.apply {
//
//            urlImageFull?.let {
//                binding.partyPicture.load(urlImageFull) {
//                    crossfade(true)
//                    error(R.drawable.rick)
//                }
//            } ?: kotlin.run {
//                binding.partyPicture.setImageDrawable(getDrawable(R.drawable.rick))
//            }
//
//            binding.partyName.text = nameParty
//
//            val dateStart = OffsetDateTime.parse(dateStart).toLocalDateTime()
//            val dateEnd = OffsetDateTime.parse(dateEnd).toLocalDateTime()
//            binding.partyDate.text = "${DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(dateStart)} - ${DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(dateEnd)}"
//
//            if (textLocation != "Unknnown") {
//                binding.partyLocation.text = nameTown
//                binding.locationTitle.visibility = View.VISIBLE
//                binding.locationFull.visibility = View.VISIBLE
//                binding.locationFull.text = textLocation
//            } else {
//                binding.locationTitle.visibility = View.GONE
//                binding.locationFull.visibility = View.GONE
//                binding.partyLocation.text = textLocation
//            }
//            binding.partyLocation.setOnClickListener {
//                val gmmIntentUri = Uri.parse("geo:${party.geoLat},${party.geoLon}")
//                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
//                mapIntent.`package` = "com.google.android.apps.maps"
//                startActivity(mapIntent)
//            }
//
//            if (textLineup.isNotEmpty()) {
//                binding.partyLineup.text = textLineup
//                Linkify.addLinks(binding.partyLineup, Linkify.EMAIL_ADDRESSES or Linkify.WEB_URLS)
//            }
//
//            if (textMore.isNotEmpty()) {
//                binding.partyInfo.text = textMore
//                Linkify.addLinks(binding.partyInfo, Linkify.EMAIL_ADDRESSES or Linkify.WEB_URLS)
//            }
//
//            if (textDeco.isNotEmpty()) {
//                binding.partyDeco.text = textDeco
//                Linkify.addLinks(binding.partyDeco, Linkify.EMAIL_ADDRESSES or Linkify.WEB_URLS)
//            }
//
//            if (textEntryFee.isNotEmpty()) {
//                binding.partyFee.text = textEntryFee
//            }
//
//            if (nameOrganizer.isNotEmpty()) {
//                binding.partyOrganizer.text = "${nameOrganizer}"
//            }
//            urlOrganizer?.let {
//                binding.partyOrganizer.text = "${binding.partyOrganizer.text}\n${urlOrganizer}"
//            }
//            Linkify.addLinks(binding.partyOrganizer, Linkify.EMAIL_ADDRESSES or Linkify.WEB_URLS)
//        }
//    }
}