package com.onionsquare.goabase.feature.countries

import androidx.lifecycle.Observer
import com.onionsquare.goabase.BaseViewModelTest
import com.onionsquare.goabase.TestUtils
import com.onionsquare.goabase.domain.repository.GoabaseRemoteRepository
import com.onionsquare.goabase.domain.usecase.CountriesUseCase
import com.onionsquare.goabase.network.GoaBaseApi
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.spyk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Before
import org.junit.Test


class CountriesViewModelViewModelTest : BaseViewModelTest() {

    lateinit var viewModel: CountriesViewModel

    @MockK
    lateinit var api: GoaBaseApi

    var countriesObserver: Observer<CountriesScreenState> = spyk(Observer { })

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        val repository = GoabaseRemoteRepository(api)
        val useCase = CountriesUseCase(repository)
        viewModel = CountriesViewModel(useCase).apply {
            countries.observeForever(countriesObserver)
        }
    }

    @Test
    fun `get countries list ok`() {
        coEvery { api.getCountries(any()) } returns TestUtils.buildCountriesResponseOk()

        viewModel.fetchCountries()

        val res = mutableListOf<CountriesScreenState>()
        verify { countriesObserver.onChanged(capture(res)) }

        assertThat(res[0]).isInstanceOf(CountriesScreenState.Init::class.java)
        assertThat(res[1]).isInstanceOf(CountriesScreenState.Loading::class.java)
        assertThat(res[2]).isInstanceOf(CountriesScreenState.ListCountriesSuccess::class.java)

        (res[2] as CountriesScreenState.ListCountriesSuccess).apply {
            assertThat(countries[0].isoCountry).isEqualTo("DE")
            assertThat(countries[1].isoCountry).isEqualTo("FR")
            assertThat(countries[2].isoCountry).isEqualTo("IT")
            assertThat(countries[3].isoCountry).isEqualTo("TH")
        }
    }

    @Test
    fun `get countries fail`() {
        coEvery { api.getCountries(any()) } returns TestUtils.buildError401Response()

        viewModel.fetchCountries()

        val res = mutableListOf<CountriesScreenState>()
        verify { countriesObserver.onChanged(capture(res)) }

        assertThat(res[0]).isInstanceOf(CountriesScreenState.Init::class.java)
        assertThat(res[1]).isInstanceOf(CountriesScreenState.Loading::class.java)
        assertThat(res[2]).isInstanceOf(CountriesScreenState.Error::class.java)
    }

    @Test
    fun `get countries fail then success`() {
        coEvery { api.getCountries(any()) } returns TestUtils.buildError401Response()

        viewModel.fetchCountries()

        coEvery { api.getCountries(any()) } returns TestUtils.buildCountriesResponseOk()

        viewModel.fetchCountries()

        val res = mutableListOf<CountriesScreenState>()
        verify { countriesObserver.onChanged(capture(res)) }

        assertThat(res[0]).isInstanceOf(CountriesScreenState.Init::class.java)
        assertThat(res[1]).isInstanceOf(CountriesScreenState.Loading::class.java)
        assertThat(res[2]).isInstanceOf(CountriesScreenState.Error::class.java)
        assertThat(res[3]).isInstanceOf(CountriesScreenState.Loading::class.java)
        assertThat(res[4]).isInstanceOf(CountriesScreenState.ListCountriesSuccess::class.java)
    }
}