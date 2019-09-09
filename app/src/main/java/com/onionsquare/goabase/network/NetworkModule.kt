package com.onionsquare.goabase.network

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory



fun provideDefaultOkhttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor())
            .build()
}

fun provideRetrofit(client: OkHttpClient): Retrofit {
    return Retrofit.Builder()
            .baseUrl("https://www.goabase.net/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
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
