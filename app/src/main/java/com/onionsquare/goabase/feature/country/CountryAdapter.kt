package com.onionsquare.goabase.feature.country

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.heetch.countrypicker.Utils
import com.onionsquare.goabase.R
import com.onionsquare.goabase.model.Country
import kotlinx.android.synthetic.main.country_item.view.*

class CountryAdapter(private val items: ArrayList<Country>, private val listener: CountryClickListener) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>(), Observer<List<Country>> {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.country_item, parent, false), listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun onChanged(countries: List<Country>) {
        items.clear()
        items.addAll(countries)
        notifyDataSetChanged()
    }

    class CountryViewHolder(itemView: View, private val listener: CountryClickListener) : RecyclerView.ViewHolder(itemView) {

        fun bind(country: Country) {
            itemView.country_name.text = country.nameCountry
            itemView.party_count.text = itemView.context.resources.getQuantityString(R.plurals.numberOfParties, country.numParties.toInt(), country.numParties.toInt())

            var drawable: Drawable? = ContextCompat.getDrawable(itemView.context, Utils.getMipmapResId(itemView.context, country.isoCountry.toLowerCase() + "_flag"))
            if (drawable != null) {
                itemView.party_country_flag.setImageDrawable(drawable)
            }
            itemView.setOnClickListener { listener.onCountrySelected(country) }
        }
    }

    interface CountryClickListener {
        fun onCountrySelected(country: Country)
    }
}