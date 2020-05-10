package com.biodun.bitcoinChart.data.repository

import com.biodun.bitcoinChart.data.local.ReactiveDb
import com.biodun.bitcoinChart.data.model.BitcoinData
import com.biodun.bitcoinChart.data.remote.retrofitRemoteImpl.BitcoinChartRemoteDataSource
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class BitcoinChartRepositoryImpl @Inject constructor(
    private val remoteDataSource: BitcoinChartRemoteDataSource,
    private val reactiveDb: ReactiveDb<BitcoinData>
) : BitcoinChartRepository {

    override fun getBitcoinData(duration: String): Single<BitcoinData> =
        remoteDataSource.getRemoteBitcoinData(duration)

    override fun getLocalBitcoinData(): Flowable<BitcoinData> =
        reactiveDb.getAllBitcoinData()
}
