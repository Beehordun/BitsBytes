package com.biodun.core.di

import com.biodun.core.scheduler.AppScheduler
import com.biodun.core.scheduler.SchedulerInterface
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val baseUrl = "https://api.blockchain.info"

@Module
interface CoreModule {

    companion object {

        @Provides
        fun provideOkHttpClient(): OkHttpClient = OkHttpClient()

        @Provides
        fun provideGson(): Gson = Gson()

        @Singleton
        @Provides
        fun provideRetrofit(okHttpClient: OkHttpClient, gson: Gson): Retrofit {
            return Retrofit.Builder().baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }

        @Provides
        @Singleton
        fun provideAppScheduler(): SchedulerInterface = AppScheduler()
    }
}
