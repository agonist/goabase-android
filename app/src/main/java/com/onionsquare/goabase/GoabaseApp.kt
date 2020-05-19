package com.onionsquare.goabase

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.onionsquare.goabase.feature.country.CountriesRepository
import com.onionsquare.goabase.feature.country.CountriesViewModel
import com.onionsquare.goabase.feature.parties.PartiesRepository
import com.onionsquare.goabase.feature.parties.PartiesViewModel
import com.onionsquare.goabase.feature.partydetails.PartyDetailsRepository
import com.onionsquare.goabase.feature.partydetails.PartyDetailsViewModel
import com.onionsquare.goabase.network.goabaseeNetworkModule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

@ExperimentalCoroutinesApi
class GoabaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        setupKoin()
    }

    val repositoryModule = module {
        factory { CountriesRepository(get()) }
        factory { PartiesRepository(get()) }
        factory { PartyDetailsRepository(get()) }
    }

    val viewModelModule = module {
        viewModel { CountriesViewModel(get()) }
        viewModel { PartiesViewModel(get()) }
        viewModel { PartyDetailsViewModel(get()) }
    }

    private fun setupKoin() {
        startKoin {
            modules(listOf(goabaseeNetworkModule, repositoryModule, viewModelModule))
        }
    }
}