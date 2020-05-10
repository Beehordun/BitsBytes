package com.biodun.bitcoinChart.data.repository

import com.biodun.bitcoinChart.data.local.ReactiveDb
import com.biodun.bitcoinChart.data.model.BitcoinData
import com.biodun.bitcoinChart.data.remote.retrofitRemoteImpl.BitcoinChartRemoteDataSource
import io.reactivex.Single
import javax.inject.Inject

class BitcoinChartRepositoryImpl @Inject constructor(
    private val remoteDataSource: BitcoinChartRemoteDataSource,
    private val reactiveDb: ReactiveDb<BitcoinData>
) : BitcoinChartRepository {

    override fun getBitcoinData(duration: String): Single<BitcoinData> {
       return remoteDataSource.getRemoteBitcoinData(duration)
    }
}
