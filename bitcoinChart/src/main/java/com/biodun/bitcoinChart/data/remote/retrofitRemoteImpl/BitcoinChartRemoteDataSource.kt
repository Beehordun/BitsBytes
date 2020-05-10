package com.biodun.bitcoinChart.data.remote.retrofitRemoteImpl

import com.biodun.bitcoinChart.data.model.BitcoinData
import io.reactivex.Single

interface BitcoinChartRemoteDataSource {
    fun getRemoteBitcoinData(duration: String): Single<BitcoinData>
}
