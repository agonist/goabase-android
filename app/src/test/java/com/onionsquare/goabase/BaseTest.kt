package com.onionsquare.goabase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import org.assertj.core.api.Assertions
import org.junit.Rule
import org.junit.rules.TestRule
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito

open class BaseTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

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