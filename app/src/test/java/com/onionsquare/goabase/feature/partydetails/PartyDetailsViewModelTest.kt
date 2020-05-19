package com.onionsquare.goabase.feature.partydetails

import androidx.lifecycle.Observer
import com.onionsquare.DumbApiRepository
import com.onionsquare.goabase.BaseTest
import com.onionsquare.goabase.model.Party
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class PartyDetailsViewModelTest : BaseTest() {

    lateinit var viewModel: PartyDetailsViewModel

    @Mock
    lateinit var partyObserver: Observer<Party>

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        viewModel = PartyDetailsViewModel(PartyDetailsRepository(DumbApiRepository()))

        viewModel.loading.observeForever(loadingObserver)
        viewModel.party.observeForever(partyObserver)
    }

    @Test
    fun `get party by id`() {
        viewModel.setPartyId("1")

        verifyLoading(2, booleanArrayOf(true, false))
        partyArgumentCaptor().run {
            Mockito.verify(partyObserver, Mockito.times(1)).onChanged(capture())
            Assertions.assertThat(allValues[0].id).isEqualTo("1")
        }
    }

    private fun partyArgumentCaptor(): ArgumentCaptor<Party> =
            ArgumentCaptor.forClass(Party::class.java)


}