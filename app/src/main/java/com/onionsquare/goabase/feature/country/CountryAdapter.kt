package com.onionsquare.goabase.feature.country

import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.heetch.countrypicker.Utils
import com.onionsquare.goabase.R
import com.onionsquare.goabase.model.Country

class CountryAdapter(val items: List<Country>, val listener: CountryClickListener) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        return CountryViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.country_item, parent, false), listener)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class CountryViewHolder(itemView: View, val listener: CountryClickListener) : RecyclerView.ViewHolder(itemView) {

        var name = itemView.findViewById<TextView>(R.id.country_name)
        var count = itemView.findViewById<TextView>(R.id.party_count)
        var flag = itemView.findViewById<ImageView>(R.id.party_country_flag)

        fun bind(country: Country) {
            name.text = country.nameCountry
            count.text = itemView.context.resources.getQuantityString(R.plurals.numberOfParties, country.numParties.toInt(), country.numParties.toInt())

            var drawable: Drawable? = ContextCompat.getDrawable(itemView.context, Utils.getMipmapResId(itemView.context, country.isoCountry.toLowerCase() + "_flag"))
            if (drawable != null) {
                flag.setImageDrawable(drawable)
            }
            itemView.setOnClickListener { listener.onClick(country) }
        }
    }

    interface CountryClickListener {
        fun onClick(country: Country)
    }

}