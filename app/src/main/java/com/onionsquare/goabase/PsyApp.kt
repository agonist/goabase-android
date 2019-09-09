package com.onionsquare.goabase

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.jakewharton.threetenabp.AndroidThreeTen
import com.onionsquare.goabase.feature.country.CountriesViewModel
import com.onionsquare.goabase.feature.parties.PartiesViewModel
import com.onionsquare.goabase.feature.partydetails.PartyDetailsViewModel
import com.onionsquare.goabase.network.provideDefaultOkhttpClient
import com.onionsquare.goabase.network.provideGoabaseService
import com.onionsquare.goabase.network.provideRetrofit
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module

class PsyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Fresco.initialize(this)
        AndroidThreeTen.init(this)
        setupKoin()
    }

    val goabaseeNetworkModule = module {

        single { provideDefaultOkhttpClient() }
        single { provideRetrofit(get()) }
        single { provideGoabaseService(get()) }
        factory(named("MAIN_THREAD_SCHEDULER")) { AndroidSchedulers.mainThread() }
        factory(named("IO_SCHEDULER")) { Schedulers.io() }
    }

    val goabaseModule = module {
        viewModel { CountriesViewModel(get(), get(named("IO_SCHEDULER")), get(named("MAIN_THREAD_SCHEDULER"))) }
        viewModel { PartiesViewModel(get(), get(named("IO_SCHEDULER")), get(named("MAIN_THREAD_SCHEDULER"))) }
        viewModel { PartyDetailsViewModel(get(), get(named("IO_SCHEDULER")), get(named("MAIN_THREAD_SCHEDULER"))) }
    }

    private fun setupKoin() {
        startKoin {
            modules(listOf(goabaseeNetworkModule, goabaseModule))
        }
    }
}