package com.onionsquare.goabase.feature.country

import androidx.lifecycle.Observer
import com.onionsquare.goabase.BaseViewModelTest
import com.onionsquare.goabase.model.Country
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.inject
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.mock.MockProviderRule
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class CountriesViewModelViewModelTest : BaseViewModelTest() {

    val viewModel: CountriesViewModel by inject()

    @Mock
    lateinit var countriesObserver: Observer<List<Country>>

    override fun providesKoinModules(): Module {
        return module {
            factory { CountriesRepository(get()) }
            viewModel { CountriesViewModel(get()) }
        }
    }

    @get:Rule
    val mockProvider = MockProviderRule.create { clazz ->
        Mockito.mock(clazz.java)
    }

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        viewModel.countries.observeForever(countriesObserver)
        viewModel.loading.observeForever(loadingObserver)
    }

    @Test
    fun `get countries list ok`() {
        viewModel.getCountriesAll()
        verifyLoading(2, booleanArrayOf(true, false))
        verifyCountriesStateOk()
    }

//    @Test
//    fun `get countries list error`() {
//        viewModel.getCountriesAll("err")
//        verifyCountriesStateError()
//    }


    protected fun verifyCountriesStateOk() {
        listArgumentCaptor().run {
            Mockito.verify(countriesObserver, Mockito.times(1)).onChanged(capture())
            assertThat( allValues[0].size).isEqualTo(4)
            assertThat( allValues[0][0].isoCountry).isEqualTo("IT")
        }
    }

//    protected fun verifyCountriesStateError() {
//        listArgumentCaptor().run {
//            Mockito.verify(countriesObserver, Mockito.times(2)).onChanged(capture())
//
//            assertThat(allValues[0]::class.java).isEqualTo(CountriesData.Loading::class.java)
//            assertThat(allValues[1]::class.java).isEqualTo(CountriesData.Error::class.java)
//        }
//    }

    private fun listArgumentCaptor(): ArgumentCaptor<List<Country>> {
        val list = listOf<Country>()
        return ArgumentCaptor.forClass(list::class.java)
    }
}