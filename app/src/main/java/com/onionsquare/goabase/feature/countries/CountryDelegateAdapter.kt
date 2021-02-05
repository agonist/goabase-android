package com.onionsquare.goabase.feature.countries

import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.heetch.countrypicker.Utils
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter
import com.onionsquare.goabase.R
import com.onionsquare.goabase.databinding.CountryItemBinding
import com.onionsquare.goabase.model.Country

class CountryDelegateAdapter(val onClick: (country: Country) -> Unit) :
        ViewBindingDelegateAdapter<Country, CountryItemBinding>(CountryItemBinding::inflate) {

    override fun isForViewType(item: Any): Boolean = item is Country

    override fun CountryItemBinding.onBind(item: Country) {
        countryName.text = item.nameCountry
        partyCount.text = root.context.resources.getQuantityString(R.plurals.numberOfParties, item.numParties.toInt(), item.numParties.toInt())
        val drawable: Drawable? = ContextCompat.getDrawable(root.context, Utils.getMipmapResId(root.context, item.isoCountry.toLowerCase() + "_flag"))
        if (drawable != null) {
            partyCountryFlag.setImageDrawable(drawable)
        }
        root.setOnClickListener { onClick(item) }
    }

    override fun Country.getItemId(): Any {
        return nameCountry
    }
}