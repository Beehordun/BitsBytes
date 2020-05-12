package com.biodun.bitcoinChart.data.repository

import com.biodun.bitcoinChart.data.local.ReactiveDb
import com.biodun.bitcoinChart.data.model.BitcoinData
import com.biodun.bitcoinChart.data.remote.retrofitRemoteImpl.BitcoinChartRemoteDataSource
import com.biodun.core.scheduler.SchedulerInterface
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class BitcoinChartRepositoryImpl @Inject constructor(
    private val remoteDataSource: BitcoinChartRemoteDataSource,
    private val reactiveDb: ReactiveDb<BitcoinData>,
    private val appScheduler: SchedulerInterface
) : BitcoinChartRepository {

    override fun getBitcoinData(duration: String): Single<BitcoinData> =
        remoteDataSource.getRemoteBitcoinData(duration)
            .subscribeOn(appScheduler.io())
            .observeOn(appScheduler.computation())
            .doOnSuccess {
                reactiveDb.insertAllBitcoinData(it)
            }

    override fun getLocalBitcoinData(): Flowable<BitcoinData> =
        reactiveDb.getAllBitcoinData()
}
