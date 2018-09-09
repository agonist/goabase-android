package com.onionsquare.goabase.feature.parties

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.facebook.drawee.view.SimpleDraweeView
import com.onionsquare.goabase.R
import com.onionsquare.goabase.model.Party
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle


class PartiesAdapter(val items: List<Party>, val listener: PartyClickListener) : RecyclerView.Adapter<PartiesAdapter.PartyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PartyViewHolder {
        return PartyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.party_item, parent, false), listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: PartyViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class PartyViewHolder(itemView: View, val listener: PartyClickListener) : RecyclerView.ViewHolder(itemView) {

        val partyName = itemView.findViewById<TextView>(R.id.party_name)
        val partyPicture = itemView.findViewById<SimpleDraweeView>(R.id.party_picture)
        val partyDate = itemView.findViewById<TextView>(R.id.party_date)
        val partyCountry = itemView.findViewById<TextView>(R.id.party_country)

        fun bind(party: Party) {
            partyName.text = party.nameParty
            partyCountry.text = " ${party.nameTown}"

            val date = OffsetDateTime.parse(party.dateStart).toLocalDateTime()
            val txt = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(date)
            partyDate.text = txt
            partyPicture.setImageURI(party.urlImageMedium)

            itemView.setOnClickListener {
                listener.onClick(party)
            }
        }
    }

    interface PartyClickListener {
        fun onClick(party: Party)
    }
}