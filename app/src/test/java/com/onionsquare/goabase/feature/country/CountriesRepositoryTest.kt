package com.onionsquare.goabase.feature.country

import com.onionsquare.DumbApiRepository
import com.onionsquare.goabase.GoabaseException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class CountriesRepositoryTest {

    lateinit var countriesRepository: CountriesRepository

    @Before
    fun init() {
        countriesRepository = CountriesRepository(DumbApiRepository())
    }

    @Test
    fun `countries are sorted by parties number ok`() = runBlockingTest {
        countriesRepository.listAllCountriesSortedByPartiesAmount("list-all").collect { res ->
            when (res) {
                is CountriesData.Success -> {
                    assertThat(res.countries[0].isoCountry).isEqualTo("IT")
                    assertThat(res.countries[1].isoCountry).isEqualTo("FR")
                    assertThat(res.countries[2].isoCountry).isEqualTo("DE")
                    assertThat(res.countries[3].isoCountry).isEqualTo("TH")
                }
            }
        }
    }

    @Test
    fun `countries are sorted by parties number not ok`() = runBlockingTest {
        countriesRepository.listAllCountriesSortedByPartiesAmount("error").collect { res ->
            when (res) {
                is CountriesData.Error -> {
                    assertThat(res.e::class.java).isEqualTo(GoabaseException::class.java)
                }
            }
        }
    }
}