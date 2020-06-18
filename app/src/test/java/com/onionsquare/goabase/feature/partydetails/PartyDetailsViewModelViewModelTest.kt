package com.onionsquare.goabase.feature.partydetails

import com.onionsquare.goabase.BaseRepository
import com.onionsquare.goabase.BaseViewModelTest
import com.onionsquare.goabase.TestUtils
import com.onionsquare.goabase.domain.repository.GoabaseRemoteRepository
import com.onionsquare.goabase.domain.usecase.PartyUseCase
import com.onionsquare.goabase.domain.usecase.State
import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.network.GoaBaseApi
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.test.runBlockingTest
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class PartyDetailsViewModelViewModelTest : BaseViewModelTest() {

    lateinit var viewModel: PartyDetailsViewModel

    @Mock
    lateinit var api: GoaBaseApi

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        val repository = GoabaseRemoteRepository(api)
        val useCase = PartyUseCase(repository)
        viewModel = PartyDetailsViewModel(useCase)
    }

    @Test
    fun `get party by id`() = runBlockingTest {
        Mockito.`when`(api.getParty("xxx")).thenReturn(TestUtils.buildPartyResponseOk())

        val res = arrayListOf<State<Party>>()

        viewModel.party
                .take(3)
                .onEach { res.add(it) }
                .launchIn(this)

        viewModel.getPartyById("xxx")

        Assertions.assertThat(res[0]).isInstanceOf(State.Init::class.java)
        Assertions.assertThat(res[1]).isInstanceOf(State.Loading::class.java)
        Assertions.assertThat(res[2]).isInstanceOf(State.Success::class.java)

        val data = (res[2] as State.Success).data
        Assertions.assertThat(data.id).isEqualTo("105521")
    }


    @Test
    fun `get party by id fail`() = runBlockingTest {
        Mockito.`when`(api.getParty("xxx")).thenReturn(TestUtils.buildError401Response())

        val res = arrayListOf<State<Party>>()

        viewModel.party
                .take(3)
                .onEach { res.add(it) }
                .launchIn(this)

        viewModel.getPartyById("xxx")

        Assertions.assertThat(res[0]).isInstanceOf(State.Init::class.java)
        Assertions.assertThat(res[1]).isInstanceOf(State.Loading::class.java)
        Assertions.assertThat(res[2]).isInstanceOf(State.Error::class.java)

        val error = (res[2] as State.Error).error
        Assertions.assertThat(error).isEqualTo(BaseRepository.ErrorType.UNKNOWN)
    }
}