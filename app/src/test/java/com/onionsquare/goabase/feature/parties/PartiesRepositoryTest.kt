package com.onionsquare.goabase.feature.parties

import com.onionsquare.DumbApiRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class PartiesRepositoryTest {

    lateinit var partiesRepository: PartiesRepository

    @Before
    fun init() {
        partiesRepository = PartiesRepository(DumbApiRepository())
    }

    @Test
    fun `get parties by country`() = runBlockingTest {

        partiesRepository.getPartiesByCountry("FR").collect { res ->
            when (res) {
                is PartiesData.Success -> assertThat(res.parties.size, `is`(3))
            }
        }
    }
}