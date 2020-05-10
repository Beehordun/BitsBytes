package com.biodun.bitcoinChart.data.remote

import com.biodun.bitcoinChart.data.model.BitcoinData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface BitcoinChartService {

    @GET("/charts/transactions-per-second")
    fun getBitcoinData(@QueryMap query: Map<String, String>): Single<BitcoinData>
}
