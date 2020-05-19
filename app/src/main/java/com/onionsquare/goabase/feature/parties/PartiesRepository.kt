package com.onionsquare.goabase.feature.parties

import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.network.GoaBaseApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PartiesRepository(private val api: GoaBaseApi) {

    fun getPartiesByCountry(country: String): Flow<List<Party>> =
            flow {
                val parties = api.getPartiesByCountry(country).parties
                emit(parties)
            }
}