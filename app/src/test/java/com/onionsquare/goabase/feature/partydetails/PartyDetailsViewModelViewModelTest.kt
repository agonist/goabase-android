package com.onionsquare.goabase.feature.partydetails

import androidx.lifecycle.Observer
import com.onionsquare.goabase.BaseViewModelTest
import com.onionsquare.goabase.model.Party
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.inject
import org.koin.core.module.Module
import org.koin.dsl.module
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class PartyDetailsViewModelViewModelTest : BaseViewModelTest() {

    val viewModel: PartyDetailsViewModel by inject()

    @Mock
    lateinit var partyObserver: Observer<Party>

    override fun providesKoinModules(): Module {
        return module {
            factory { PartyDetailsRepository(get()) }
            viewModel { PartyDetailsViewModel(get()) }
        }
    }

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        viewModel.loading.observeForever(loadingObserver)
        viewModel.party.observeForever(partyObserver)
    }

    @Test
    fun `get party by id`() {
        viewModel.getPartyById("1")

        verifyLoading(2, booleanArrayOf(true, false))
        partyArgumentCaptor().run {
            Mockito.verify(partyObserver, Mockito.times(1)).onChanged(capture())
            Assertions.assertThat(allValues[0].id).isEqualTo("1")
        }
    }

    private fun partyArgumentCaptor(): ArgumentCaptor<Party> =
            ArgumentCaptor.forClass(Party::class.java)

}