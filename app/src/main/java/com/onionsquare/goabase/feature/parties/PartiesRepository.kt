package com.onionsquare.goabase.feature.parties

import com.onionsquare.goabase.BaseRepository
import com.onionsquare.goabase.Result
import com.onionsquare.goabase.model.Party
import com.onionsquare.goabase.network.GoaBaseApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PartiesRepository(private val api: GoaBaseApi) : BaseRepository() {

    fun getPartiesByCountry(country: String): Flow<PartiesData<Any>> =
            flow {
                when (val result = apiCall { api.getPartiesByCountry(country) }) {
                    is Result.Success -> emit(PartiesData.Success(result.data.parties))
                    is Result.Error -> emit(PartiesData.Error(result.exception))
                }
            }
}

sealed class PartiesData<out T : Any> {
    data class Success(val parties: List<Party>) : PartiesData<List<Party>>()
    data class Error(val e: Exception) : PartiesData<Nothing>()
}