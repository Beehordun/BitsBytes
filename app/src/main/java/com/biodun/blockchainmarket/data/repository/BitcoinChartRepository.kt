package com.biodun.blockchainmarket.data.repository

import com.biodun.blockchainmarket.data.model.BitcoinData
import io.reactivex.Flowable
import io.reactivex.Single

interface BitcoinChartRepository {
    fun getBitcoinData(duration: String): Single<BitcoinData>
    fun getLocalBitcoinData(): Flowable<BitcoinData>
}
