package com.onionsquare.goabase.feature.parties

import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import com.onionsquare.goabase.BaseViewModelTest
import com.onionsquare.goabase.TestUtils
import com.onionsquare.goabase.domain.repository.GoabaseRemoteRepository
import com.onionsquare.goabase.domain.usecase.PartiesUseCase
import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.network.GoaBaseApi
import io.mockk.*
import io.mockk.impl.annotations.MockK
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test


class PartiesViewModelViewModelTest : BaseViewModelTest() {

    lateinit var viewModel: PartiesViewModel

    @MockK
    lateinit var api: GoaBaseApi

    private var partiesObserver: Observer<PartiesScreenState> = spyk(Observer { })
    var userActionsObserver: Observer<PartiesScreenActions> = spyk(Observer { })

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        val repository = GoabaseRemoteRepository(api)
        val useCase = PartiesUseCase(repository)
        viewModel = PartiesViewModel(useCase).apply {
            parties.observeForever(partiesObserver)
        }
        viewModel.userActions.asLiveData().observeForever(userActionsObserver)
    }

    @Test
    fun `get parties list ok`() {
        coEvery { api.getPartiesByCountry(any()) } returns TestUtils.buildPartiesResponseOk()

        viewModel.setCountry("France")

        val res = mutableListOf<PartiesScreenState>()
        verify { partiesObserver.onChanged(capture(res)) }

        assertThat(res[0]).isInstanceOf(PartiesScreenState.Init::class.java)
        assertThat(res[1]).isInstanceOf(PartiesScreenState.Loading::class.java)
        assertThat(res[2]).isInstanceOf(PartiesScreenState.ListPartiesSuccess::class.java)
    }

    @Test
    fun `get parties fail`() {
        coEvery { api.getPartiesByCountry(any()) } returns TestUtils.buildError401Response()

        viewModel.setCountry("France")

        val res = mutableListOf<PartiesScreenState>()
        verify { partiesObserver.onChanged(capture(res)) }

        assertThat(res[0]).isInstanceOf(PartiesScreenState.Init::class.java)
        assertThat(res[1]).isInstanceOf(PartiesScreenState.Loading::class.java)
        assertThat(res[2]).isInstanceOf(PartiesScreenState.Error::class.java)
    }

    @Test
    fun `get parties fail then success`() {
        coEvery { api.getPartiesByCountry(any()) } returns TestUtils.buildError401Response()

        viewModel.setCountry("France")

        coEvery { api.getPartiesByCountry(any()) } returns TestUtils.buildPartiesResponseOk()

        viewModel.fetchParties()

        val res = mutableListOf<PartiesScreenState>()
        verify { partiesObserver.onChanged(capture(res)) }

        assertThat(res[0]).isInstanceOf(PartiesScreenState.Init::class.java)
        assertThat(res[1]).isInstanceOf(PartiesScreenState.Loading::class.java)
        assertThat(res[2]).isInstanceOf(PartiesScreenState.Error::class.java)
        assertThat(res[3]).isInstanceOf(PartiesScreenState.Loading::class.java)
        assertThat(res[4]).isInstanceOf(PartiesScreenState.ListPartiesSuccess::class.java)
    }

    @Test
    fun `test user actions`() {
        val party: Party = mockkClass(Party::class)
        viewModel.onPartyClicked(party)
        viewModel.navigateUp()

        val res = mutableListOf<PartiesScreenActions>()
        verify { userActionsObserver.onChanged(capture(res)) }

        assertThat(res[0]).isInstanceOf(PartiesScreenActions.PartyClicked::class.java)
        assertThat(res[1]).isInstanceOf(PartiesScreenActions.NavigateUp::class.java)
    }
}