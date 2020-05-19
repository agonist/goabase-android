package com.onionsquare.goabase.feature.country

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.onionsquare.DumbApiRepository
import com.onionsquare.goabase.BaseTest
import com.onionsquare.goabase.model.Country
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


@ExperimentalCoroutinesApi
class CountriesViewModelTest: BaseTest() {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    lateinit var viewModel: CountriesViewModel


    @Mock
    lateinit var countriesObserver: Observer<List<Country>>

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        viewModel = CountriesViewModel(CountriesRepository(DumbApiRepository()))

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
            Assertions.assertThat(allValues[0].size).isEqualTo(4)
        }
    }


}