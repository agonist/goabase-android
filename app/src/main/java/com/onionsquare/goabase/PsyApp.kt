package com.onionsquare.goabase

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.onionsquare.goabase.feature.country.CountriesRepository
import com.onionsquare.goabase.feature.country.CountriesViewModel
import com.onionsquare.goabase.feature.parties.PartiesViewModel
import com.onionsquare.goabase.feature.partydetails.PartyDetailsViewModel
import com.onionsquare.goabase.network.goabaseeNetworkModule
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class PsyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        setupKoin()
    }

    val goabaseModule = module {
        viewModel { CountriesViewModel(CountriesRepository(get())) }
        viewModel { PartiesViewModel(get()) }
        viewModel { PartyDetailsViewModel(get()) }
    }

    private fun setupKoin() {
        startKoin {
            modules(listOf(goabaseeNetworkModule, goabaseModule))
        }
    }
}