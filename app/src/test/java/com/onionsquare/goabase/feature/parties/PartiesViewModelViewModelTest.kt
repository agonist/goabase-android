package com.onionsquare.goabase.feature.parties

import com.onionsquare.goabase.BaseRepository
import com.onionsquare.goabase.BaseViewModelTest
import com.onionsquare.goabase.TestUtils
import com.onionsquare.goabase.domain.repository.GoabaseRemoteRepository
import com.onionsquare.goabase.domain.usecase.PartiesUseCase
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

class PartiesViewModelViewModelTest : BaseViewModelTest() {

    lateinit var viewModel: PartiesViewModel

    @Mock
    lateinit var api: GoaBaseApi

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        val repository = GoabaseRemoteRepository(api)
        val useCase = PartiesUseCase(repository)
        viewModel = PartiesViewModel(useCase)
    }

    @Test
    fun `test get parties by country`() = runBlockingTest {
        Mockito.`when`(api.getPartiesByCountry("FR")).thenReturn(TestUtils.buildPartiesResponseOk())

        val res = arrayListOf<State<List<Party>>>()

        viewModel.parties
                .take(3)
                .onEach { res.add(it) }
                .launchIn(this)

        viewModel.getPartiesByCountry("FR")

        Assertions.assertThat(res[0]).isInstanceOf(State.Init::class.java)
        Assertions.assertThat(res[1]).isInstanceOf(State.Loading::class.java)
        Assertions.assertThat(res[2]).isInstanceOf(State.Success::class.java)

        val datas = (res[2] as State.Success).data
        Assertions.assertThat(datas.size).isEqualTo(4)
    }


    @Test
    fun `test get parties by country fail`() = runBlockingTest {
        Mockito.`when`(api.getPartiesByCountry("FR")).thenReturn(TestUtils.buildError401Response())

        val res = arrayListOf<State<List<Party>>>()

        viewModel.parties
                .take(3)
                .onEach { res.add(it) }
                .launchIn(this)

        viewModel.getPartiesByCountry("FR")

        Assertions.assertThat(res[0]).isInstanceOf(State.Init::class.java)
        Assertions.assertThat(res[1]).isInstanceOf(State.Loading::class.java)
        Assertions.assertThat(res[2]).isInstanceOf(State.Error::class.java)

        val error = (res[2] as State.Error).error
        Assertions.assertThat(error).isEqualTo(BaseRepository.ErrorType.UNKNOWN)

    }
}