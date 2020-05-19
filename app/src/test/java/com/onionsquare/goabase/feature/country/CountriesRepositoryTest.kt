package com.onionsquare.goabase.feature.country

import com.onionsquare.DumbApiRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
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
    fun `test countries are sorted by parties number`() = runBlockingTest {

        countriesRepository.listAllCountriesSortedByPartiesAmount().collect { res ->
            assertThat(res[0].isoCountry, `is`("IT"))
            assertThat(res[1].isoCountry, `is`("FR"))
            assertThat(res[2].isoCountry, `is`("DE"))
            assertThat(res[3].isoCountry, `is`("TH"))
        }
    }
}