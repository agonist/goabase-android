package com.onionsquare.goabase

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.onionsquare.goabase.feature.country.CountriesViewModel
import com.onionsquare.goabase.feature.parties.PartiesViewModel
import com.onionsquare.goabase.feature.partydetails.PartyDetailsViewModel
import com.onionsquare.goabase.network.provideDefaultOkhttpClient
import com.onionsquare.goabase.network.provideGoabaseService
import com.onionsquare.goabase.network.provideRetrofit
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class PsyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        setupKoin()
    }

    val goabaseeNetworkModule = module {

        single { provideDefaultOkhttpClient() }
        single { provideRetrofit(get()) }
        single { provideGoabaseService(get()) }
    }

    val goabaseModule = module {
        viewModel { CountriesViewModel(get()) }
        viewModel { PartiesViewModel(get()) }
        viewModel { PartyDetailsViewModel(get()) }
    }

    private fun setupKoin() {
        startKoin {
            modules(listOf(goabaseeNetworkModule, goabaseModule))
        }
    }
}