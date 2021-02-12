package com.onionsquare.goabase.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val goabaseeNetworkModule = module {
    single { provideMoshi() }
    single { provideDefaultOkhttpClient() }
    single { provideRetrofit(get(), get()) }
    single { provideGoabaseService(get()) }
}

fun provideDefaultOkhttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor())
            .build()
}

fun provideRetrofit(client: OkHttpClient, moshi: Moshi): Retrofit {
    return Retrofit.Builder()
            .baseUrl("https://www.goabase.net/")
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
}

fun provideMoshi(): Moshi {
    return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()
}

private fun loggingInterceptor(): HttpLoggingInterceptor {
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY
    return interceptor
}

fun provideGoabaseService(retrofit: Retrofit): GoaBaseApi =
        retrofit.create(GoaBaseApi::class.java)
