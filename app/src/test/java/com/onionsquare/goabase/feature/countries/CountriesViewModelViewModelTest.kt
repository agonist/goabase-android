//package com.onionsquare.goabase.feature.countries
//
//import androidx.arch.core.executor.testing.InstantTaskExecutorRule
//import com.onionsquare.goabase.BaseRepository
//import com.onionsquare.goabase.BaseViewModelTest
//import com.onionsquare.goabase.MainCoroutineRule
//import com.onionsquare.goabase.TestUtils
//import com.onionsquare.goabase.domain.repository.GoabaseRemoteRepository
//import com.onionsquare.goabase.domain.usecase.CountriesUseCase
//import com.onionsquare.goabase.domain.usecase.State
//import com.onionsquare.goabase.model.Country
//import com.onionsquare.goabase.network.GoaBaseApi
//import kotlinx.coroutines.flow.launchIn
//import kotlinx.coroutines.flow.onEach
//import kotlinx.coroutines.flow.take
//import kotlinx.coroutines.test.runBlockingTest
//import org.assertj.core.api.Assertions.assertThat
//import org.junit.Before
//import org.junit.Rule
//import org.junit.Test
//import org.mockito.Mock
//import org.mockito.Mockito
//import org.mockito.MockitoAnnotations
//
//
//class CountriesViewModelViewModelTest : BaseViewModelTest() {
//
//    lateinit var viewModel: CountriesViewModel
//
//    @Mock
//    lateinit var api: GoaBaseApi
//
//    @Before
//    fun setup() {
//        MockitoAnnotations.initMocks(this)
//        val repository = GoabaseRemoteRepository(api)
//        val useCase = CountriesUseCase(repository)
//        viewModel = CountriesViewModel(useCase)
//    }
//
//    @Test
//    fun `get countries list ok`() = runBlockingTest {
//        Mockito.`when`(api.getCountries("list-all")).thenReturn(TestUtils.buildCountriesResponseOk())
//
//        val res = arrayListOf<State<List<Country>>>()
//
//        viewModel.countries
//                .take(3)
//                .onEach { res.add(it) }
//                .launchIn(this)
//
//        viewModel.getCountriesAll()
//
//
//        assertThat(res[0]).isInstanceOf(State.Init::class.java)
//        assertThat(res[1]).isInstanceOf(State.Loading::class.java)
//        assertThat(res[2]).isInstanceOf(State.Success::class.java)
//
//        val datas = (res[2] as State.Success).data
//        assertThat(datas.size).isEqualTo(4)
//        assertThat(datas[0].isoCountry).isEqualTo("DE")
//        assertThat(datas[1].isoCountry).isEqualTo("FR")
//        assertThat(datas[2].isoCountry).isEqualTo("IT")
//        assertThat(datas[3].isoCountry).isEqualTo("TH")
//    }
//
//    @Test
//    fun `get countries fail`() = runBlockingTest {
//        Mockito.`when`(api.getCountries("list-all")).thenReturn(TestUtils.buildError401Response())
//
//        val res = arrayListOf<State<List<Country>>>()
//
//        viewModel.countries
//                .take(3)
//                .onEach { res.add(it) }
//                .launchIn(this)
//
//        viewModel.getCountriesAll()
//
//        assertThat(res[0]).isInstanceOf(State.Init::class.java)
//        assertThat(res[1]).isInstanceOf(State.Loading::class.java)
//        assertThat(res[2]).isInstanceOf(State.Error::class.java)
//
//        val error = (res[2] as State.Error).error
//        assertThat(error).isEqualTo(BaseRepository.ErrorType.UNKNOWN)
//    }
//}