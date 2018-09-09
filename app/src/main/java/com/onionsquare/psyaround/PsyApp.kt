package com.onionsquare.goabase

import android.app.Application
import com.facebook.drawee.backends.pipeline.Fresco
import com.google.gson.GsonBuilder
import com.jakewharton.threetenabp.AndroidThreeTen
import com.onionsquare.goabase.network.GoaBaseApi
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory



class PsyApp : Application() {

    lateinit var api: GoaBaseApi

    override fun onCreate() {
        super.onCreate()
        instance = this
        initRetrofit()
        Fresco.initialize(this);
        AndroidThreeTen.init(this);

    }



    private fun initRetrofit() {
        val gson = GsonBuilder()
                .serializeNulls()
                .create()
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .build()

        val retrofit = Retrofit.Builder()
                .baseUrl("https://www.goabase.net/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(client)
                .build()
        api = retrofit.create(GoaBaseApi::class.java)
    }

    companion object {
        lateinit var instance: PsyApp
            private set
    }
}