package com.onionsquare.goabase

import com.google.gson.Gson
import com.onionsquare.goabase.model.Countries
import com.onionsquare.goabase.model.Parties
import com.onionsquare.goabase.model.PartyDetails
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import retrofit2.Response

class TestUtils {

    companion object {

        fun buildCountriesResponseOk(): Response<Countries> {
            return buildSuccessResponse(200, "countries.json", Countries::class.java)
        }

        fun buildPartiesResponseOk(): Response<Parties> {
            return buildSuccessResponse(200, "parties.json", Parties::class.java)
        }

        fun buildPartyResponseOk(): Response<PartyDetails> {
            return buildSuccessResponse(200, "party.json", PartyDetails::class.java)
        }

        fun buildCountriesResponse401(): Response<Countries> {
            return buildError401Response()
        }



        fun <T> buildSuccessResponse(
                code: Int,
                file: String,
                clazz: Class<T>
        ): Response<T> {
            val gson = Gson()
            val res = gson.fromJson(ClassLoader.getSystemResource(file).readText(), clazz)

            return Response.success(code, res)
        }

        fun <T> buildErrorResponse(code: Int, file: String): Response<T> {
            return Response.error(
                    code, ResponseBody.create(
                    "application/json".toMediaTypeOrNull(),
                    ClassLoader.getSystemResource(file).readText()
            )
            )
        }

        fun <T> buildError401Response(): Response<T> {
            return Response.error(
                    401, ResponseBody.create(
                    "application/json".toMediaTypeOrNull(),
                    "{ invalid credentials }"
            )
            )
        }
    }


}
