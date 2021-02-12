package com.onionsquare.goabase.feature.partydetails

import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.onionsquare.goabase.BaseViewModelTest
import com.onionsquare.goabase.TestUtils
import com.onionsquare.goabase.domain.repository.GoabaseRemoteRepository
import com.onionsquare.goabase.domain.usecase.PartiesUseCase
import com.onionsquare.goabase.domain.usecase.PartyUseCase
import com.onionsquare.goabase.feature.parties.PartiesScreenActions
import com.onionsquare.goabase.feature.parties.PartiesScreenState
import com.onionsquare.goabase.feature.parties.PartiesViewModel
import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.network.GoaBaseApi
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test


class PartyDetailsViewModelViewModelTest : BaseViewModelTest() {

    lateinit var viewModel: PartyDetailsViewModel

    @MockK
    lateinit var api: GoaBaseApi

    private var partiesObserver: Observer<PartyDetailsState> = spyk(Observer { })
    var userActionsObserver: Observer<PartyDetailsActions> = spyk(Observer { })

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        val repository = GoabaseRemoteRepository(api)
        val useCase = PartyUseCase(repository)
        viewModel = PartyDetailsViewModel(useCase).apply {
            partyDetails.observeForever(partiesObserver)
        }
        viewModel.userActions.asLiveData().observeForever(userActionsObserver)
    }

    @Test
    fun `get party details ok`() {
        coEvery { api.getParty(any()) } returns TestUtils.buildPartyResponseOk()

        viewModel.setPartyId("xxx")

        val res = mutableListOf<PartyDetailsState>()
        verify { partiesObserver.onChanged(capture(res)) }

        assertThat(res[0]).isInstanceOf(PartyDetailsState.Init::class.java)
        assertThat(res[1]).isInstanceOf(PartyDetailsState.Loading::class.java)
        assertThat(res[2]).isInstanceOf(PartyDetailsState.GetPartyDetailsSuccess::class.java)
    }

    @Test
    fun `get party details fail`() {
        coEvery { api.getParty(any()) } returns TestUtils.buildError401Response()

        viewModel.setPartyId("xxx")

        val res = mutableListOf<PartyDetailsState>()
        verify { partiesObserver.onChanged(capture(res)) }

        assertThat(res[0]).isInstanceOf(PartyDetailsState.Init::class.java)
        assertThat(res[1]).isInstanceOf(PartyDetailsState.Loading::class.java)
        assertThat(res[2]).isInstanceOf(PartyDetailsState.Error::class.java)
    }

    @Test
    fun `get parties fail then success`() {
        coEvery { api.getParty(any()) } returns TestUtils.buildError401Response()

        viewModel.setPartyId("xxx")

        coEvery { api.getParty(any()) } returns TestUtils.buildPartyResponseOk()

        viewModel.fetchPartyDetails()

        val res = mutableListOf<PartyDetailsState>()
        verify { partiesObserver.onChanged(capture(res)) }

        assertThat(res[0]).isInstanceOf(PartyDetailsState.Init::class.java)
        assertThat(res[1]).isInstanceOf(PartyDetailsState.Loading::class.java)
        assertThat(res[2]).isInstanceOf(PartyDetailsState.Error::class.java)
        assertThat(res[3]).isInstanceOf(PartyDetailsState.Loading::class.java)
        assertThat(res[4]).isInstanceOf(PartyDetailsState.GetPartyDetailsSuccess::class.java)
    }

}