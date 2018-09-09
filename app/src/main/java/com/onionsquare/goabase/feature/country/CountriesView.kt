package com.onionsquare.goabase.feature.country

import com.onionsquare.goabase.model.Country

interface CountriesView {

    fun showCountries(countries: List<Country>)

    fun showLoader()

    fun hideLoader()

}