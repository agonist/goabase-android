package com.onionsquare.goabase.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun provideDefaultOkhttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor())
            .build()
}

fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
            .baseUrl("https://www.goabase.net/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
}

private fun loggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return interceptor
}

fun provideGoabaseService(retrofit: Retrofit): GoaBaseApi =
        retrofit.create(GoaBaseApi::class.java)
