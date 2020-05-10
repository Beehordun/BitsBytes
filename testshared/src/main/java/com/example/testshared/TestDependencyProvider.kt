package com.example.testshared

import android.annotation.SuppressLint
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okio.Okio
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit

const val CONNECT_TIMEOUT = 10L
const val WRITE_TIMEOUT = 30L
const val READ_TIMEOUT = 10L

object TestDependencyProvider {

    fun getRetrofit(baseUrl: HttpUrl): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
                    .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS).build()
            )
            .build()
    }

    @SuppressLint("NewApi")
    fun getResponseFromJson(fileName: String): String {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-responses/$fileName.json")
        val source = Okio.buffer(Okio.source(inputStream!!))
        return source.readString(StandardCharsets.UTF_8)
    }
}
