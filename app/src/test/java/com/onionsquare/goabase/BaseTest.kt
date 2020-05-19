package com.onionsquare.goabase

import androidx.lifecycle.Observer
import org.assertj.core.api.Assertions
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito

open class BaseTest {

    @Mock
    lateinit var loadingObserver: Observer<Boolean>

    protected fun booleanArgumentCaptor(): ArgumentCaptor<Boolean> =
            ArgumentCaptor.forClass(Boolean::class.java)

    protected fun verifyLoading(count: Int, order: BooleanArray) {
        booleanArgumentCaptor().run {
            Mockito.verify(loadingObserver, Mockito.times(count)).onChanged(capture())
            Assertions.assertThat(allValues).containsExactly(*order.toTypedArray())
        }
    }

}