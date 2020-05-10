package com.biodun.bitcoinChart.data.repository

import com.biodun.bitcoinChart.data.model.BitcoinData
import io.reactivex.Single

interface BitcoinChartRepository {
    fun getBitcoinData(duration: String): Single<BitcoinData>
}
