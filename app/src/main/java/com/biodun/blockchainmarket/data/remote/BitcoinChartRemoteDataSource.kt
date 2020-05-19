package com.biodun.blockchainmarket.data.remote

import com.biodun.blockchainmarket.data.model.BitcoinData
import io.reactivex.Single

interface BitcoinChartRemoteDataSource {
    fun getRemoteBitcoinData(duration: String): Single<BitcoinData>
}
