package com.biodun.blockchainmarket.data.remote.retrofitRemoteImpl

import com.biodun.blockchainmarket.data.model.BitcoinData
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface BitcoinChartService {

    @GET("/charts/transactions-per-second")
    fun getBitcoinData(@QueryMap query: Map<String, String>): Single<BitcoinData>
}
