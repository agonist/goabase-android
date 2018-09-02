package com.onionsquare.psyaround.feature.partydetails

import android.net.Uri
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.util.Linkify
import com.onionsquare.psyaround.PsyApp
import com.onionsquare.psyaround.R
import com.onionsquare.psyaround.feature.BaseActivity
import com.onionsquare.psyaround.model.Party
import kotlinx.android.synthetic.main.party_details.*
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle


class PartyDetailsActivity : BaseActivity(), PartyDetailsView {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val partyId = intent.getStringExtra("PARTY_ID")
        PartyDetailsPresenter(this, PsyApp.instance.api).init(partyId)
    }


    override fun showPartyDetails(party: Party) {
        print("")

        party.apply {
            urlImageMedium?.let {
                val uri = Uri.parse(it)
                party_picture.setImageURI(uri)
            }
            party_name.text = nameParty

            val dateStart = OffsetDateTime.parse(dateStart).toLocalDateTime()
            val dateEnd = OffsetDateTime.parse(dateEnd).toLocalDateTime()
            party_date.text = "${DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(dateStart)} - ${DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(dateEnd)}"
            party_location.text = textLocation
            party_lineup.text = textLineup
            Linkify.addLinks(party_lineup, Linkify.EMAIL_ADDRESSES or Linkify.WEB_URLS)

            party_info.text = textMore
            party_deco.text= textDeco
            party_fee.text = textEntryFee
            party_organizer.text = "${nameOrganizer}\n${urlOrganizer}"
            Linkify.addLinks(party_organizer, Linkify.EMAIL_ADDRESSES or Linkify.WEB_URLS)
        }

    }


    override fun provideToolbarTitle(): String = getString(R.string.party_details)


    override fun provideLayout(): Int = R.layout.party_details

    override fun provideToolbar(): Toolbar? = null
}