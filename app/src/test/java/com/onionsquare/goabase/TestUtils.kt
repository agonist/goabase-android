package com.onionsquare.goabase

import com.onionsquare.goabase.model.Countries
import com.onionsquare.goabase.model.Parties
import com.onionsquare.goabase.model.PartyDetails
import com.onionsquare.goabase.network.Resource
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.Response

class TestUtils {

    companion object {

        fun buildCountriesResponseOk(): Flow<Resource<Countries>> {
            return buildSuccessResponse(200, "countries.json", Countries::class.java)
        }

        fun buildPartiesResponseOk(): Flow<Resource<Parties>> {
            return buildSuccessResponse(200, "parties.json", Parties::class.java)
        }

        fun buildPartyResponseOk(): Flow<Resource<PartyDetails>> {
            return buildSuccessResponse(200, "party.json", PartyDetails::class.java)
        }

        private fun <T> buildSuccessResponse(
                code: Int,
                file: String,
                clazz: Class<T>
        ): Flow<Resource<T>> {
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            val adapter = moshi.adapter(clazz)
            val res = adapter.fromJson(ClassLoader.getSystemResource(file).readText())!!
            return flowOf(Resource.Success(res))
        }

        fun <T> buildError401Response(): Flow<Resource<T>> {
            return flowOf(Resource.Error("{ invalid credentials }"))
        }
    }
}
