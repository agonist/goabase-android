package com.onionsquare.goabase

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.onionsquare.DumbApiRepository
import com.onionsquare.goabase.network.GoaBaseApi
import org.assertj.core.api.Assertions
import org.junit.Rule
import org.junit.rules.TestRule
import org.koin.core.module.Module
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.Mockito

abstract class BaseViewModelTest : KoinTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(baseTestModule, providesKoinModules())
    }

    val baseTestModule = module {
        single { DumbApiRepository() as GoaBaseApi }
    }

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

    abstract fun providesKoinModules(): Module

}