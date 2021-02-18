package com.onionsquare.goabase

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import com.onionsquare.goabase.domain.repository.GoabaseRemoteRepository
import com.onionsquare.goabase.domain.repository.GoabaseRepository
import com.onionsquare.goabase.domain.usecase.CountriesUseCase
import com.onionsquare.goabase.domain.usecase.PartiesUseCase
import com.onionsquare.goabase.domain.usecase.PartyUseCase
import com.onionsquare.goabase.feature.countries.CountriesViewModel
import com.onionsquare.goabase.feature.parties.PartiesViewModel
import com.onionsquare.goabase.feature.partydetails.PartyDetailsViewModel
import com.onionsquare.goabase.network.goabaseeNetworkModule
import kotlinx.coroutines.CoroutineScope
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class GoabaseApp : Application() {

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        setupKoin()
    }

    private val repositoryModule = module {
        single<GoabaseRepository> { GoabaseRemoteRepository(get()) }
    }

    private val domainModule = module {
        factory { CountriesUseCase(get()) }
        factory { PartiesUseCase(get()) }
        factory { PartyUseCase(get()) }
    }

    private val viewModelModule = module {
        viewModel { CountriesViewModel(get()) }
        viewModel { PartiesViewModel(get()) }
        viewModel { PartyDetailsViewModel(get()) }
    }

    private fun setupKoin() {
        startKoin {
            modules(listOf(goabaseeNetworkModule, repositoryModule, domainModule, viewModelModule))
        }
    }
}