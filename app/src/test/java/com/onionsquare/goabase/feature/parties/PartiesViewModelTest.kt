package com.onionsquare.goabase.feature.parties

import androidx.lifecycle.Observer
import com.onionsquare.DumbApiRepository
import com.onionsquare.goabase.BaseTest
import com.onionsquare.goabase.model.Country
import com.onionsquare.goabase.model.Party
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.assertj.core.api.Assertions
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class PartiesViewModelTest : BaseTest() {

    lateinit var viewModel: PartiesViewModel

    @Mock
    lateinit var partiesObserver: Observer<List<Party>>

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)

        viewModel = PartiesViewModel(PartiesRepository(DumbApiRepository()))

        viewModel.loading.observeForever(loadingObserver)
        viewModel.parties.observeForever(partiesObserver)
    }

    @Test
    fun `test get parties by country`() {

        viewModel.setCountry("FR")

        verifyLoading(2, booleanArrayOf(true, false))
        verifyPartiesSize(3)
    }

    private fun listArgumentCaptor(): ArgumentCaptor<List<Party>> {
        val list: List<Party> = listOf()
        return ArgumentCaptor.forClass(list::class.java)
    }

    private fun verifyPartiesSize(count: Int) {
        listArgumentCaptor().run {
            Mockito.verify(partiesObserver, Mockito.times(1)).onChanged(capture())
            Assertions.assertThat(allValues[0].size).isEqualTo(count)
        }
    }
}