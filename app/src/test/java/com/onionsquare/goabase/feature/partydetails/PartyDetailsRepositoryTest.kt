package com.onionsquare.goabase.feature.partydetails

import com.onionsquare.DumbApiRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

@ExperimentalCoroutinesApi
class PartyDetailsRepositoryTest {

    lateinit var partyDetailsRepository: PartyDetailsRepository

    @Before
    fun init() {
        partyDetailsRepository = PartyDetailsRepository(DumbApiRepository())
    }

    @Test
    fun `get party details by id`() = runBlockingTest{
        partyDetailsRepository.getPartyDetailsById("1").collect {res ->
            Assertions.assertThat(res.id).isEqualTo("1")
        }
    }
}