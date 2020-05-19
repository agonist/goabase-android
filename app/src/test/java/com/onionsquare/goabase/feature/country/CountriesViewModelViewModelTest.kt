package com.onionsquare.goabase.feature.country

import androidx.lifecycle.Observer
import com.onionsquare.goabase.BaseViewModelTest
import com.onionsquare.goabase.model.Country
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
class CountriesViewModelViewModelTest: BaseViewModelTest() {

    val viewModel: CountriesViewModel by inject()

    @Mock
    lateinit var countriesObserver: Observer<List<Country>>

    override fun providesKoinModules(): Module {
        return module {
            factory { CountriesRepository(get()) }
            viewModel { CountriesViewModel(get()) }
        }
    }

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        viewModel.loading.observeForever(loadingObserver)
        viewModel.countries.observeForever(countriesObserver)
    }

    @Test
    fun `test get countries list`() {
        verifyLoading(2, booleanArrayOf(true, false))
        verifyCountriesSize(4)
        //party count sort already tested in repository test
    }


    private fun listArgumentCaptor(): ArgumentCaptor<List<Country>> {
        val list: List<Country> = listOf()
        return ArgumentCaptor.forClass(list::class.java)
    }

    private fun verifyCountriesSize(count: Int) {
        listArgumentCaptor().run {
            Mockito.verify(countriesObserver, Mockito.times(1)).onChanged(capture())
            Assertions.assertThat(allValues[0].size).isEqualTo(count)
        }
    }
}