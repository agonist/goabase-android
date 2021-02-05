package com.onionsquare.goabase.feature.parties

import coil.load
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter
import com.onionsquare.goabase.R
import com.onionsquare.goabase.databinding.PartyItemBinding
import com.onionsquare.goabase.model.Party
import org.threeten.bp.OffsetDateTime
import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.format.FormatStyle

class PartyDelegateAdapter(val onClick: (country: Party) -> Unit) :
        ViewBindingDelegateAdapter<Party, PartyItemBinding>(PartyItemBinding::inflate) {

    override fun isForViewType(item: Any): Boolean = item is Party

    override fun PartyItemBinding.onBind(item: Party) {
        val date = OffsetDateTime.parse(item.dateStart).toLocalDateTime()
        val formatedDate = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM).format(date)

        partyName.text = item.nameParty
        partyCountry.text = item.nameTown
        partyDate.text = formatedDate
        item.urlImageMedium?.let {
            partyPicture.load(item.urlImageMedium) {
                crossfade(true)
                error(R.drawable.no_picture)
            }
        } ?: kotlin.run {
            partyPicture.setImageDrawable(root.context.getDrawable(R.drawable.no_picture))
        }
        root.setOnClickListener { onClick(item) }
    }

    override fun Party.getItemId(): Any {
        return nameCountry
    }
}