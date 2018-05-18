package com.onionsquare.psyaround.feature.country

import com.onionsquare.psyaround.model.Country

interface CountriesView {

    fun showCountries(countries: List<Country>)

}