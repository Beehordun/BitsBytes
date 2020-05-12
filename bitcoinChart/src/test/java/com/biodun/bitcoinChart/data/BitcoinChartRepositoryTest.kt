package com.biodun.bitcoinChart.data

import com.biodun.bitcoinChart.DummyData
import com.biodun.bitcoinChart.data.local.ReactiveDb
import com.biodun.bitcoinChart.data.model.BitcoinData
import com.biodun.bitcoinChart.data.remote.retrofitRemoteImpl.BitcoinChartRemoteDataSource
import com.biodun.bitcoinChart.data.repository.BitcoinChartRepository
import com.biodun.bitcoinChart.data.repository.BitcoinChartRepositoryImpl
import com.biodun.core.utils.DurationUtil
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import com.example.testshared.TestScheduler as LocalTestScheduler
import org.junit.Before
import org.junit.Test

class BitcoinChartRepositoryTest {

    private lateinit var bitcoinRepository: BitcoinChartRepository
    private val bitcoinChartRemoteDataSource: BitcoinChartRemoteDataSource = mock()
    private val reactiveDb: ReactiveDb<BitcoinData> = mock()
    private val response = DummyData.bitcoinData
    private val duration = DurationUtil.default
    private val throwable = Throwable()
    private val testScheduler = TestScheduler()

    @Before
    fun setUp() {
        bitcoinRepository =
            BitcoinChartRepositoryImpl(
                bitcoinChartRemoteDataSource,
                reactiveDb,
                LocalTestScheduler()
            )
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
    }

    @Test
    fun getRemoteBitcoinData_withDuration_returnsBitcoinDataAndSavesIntoDb() {
        whenever(bitcoinChartRemoteDataSource.getRemoteBitcoinData(duration))
            .thenReturn(Single.just(response))

        val test = bitcoinRepository.getBitcoinData(duration).test()

        verify(bitcoinChartRemoteDataSource).getRemoteBitcoinData(duration)
        verify(reactiveDb).insertAllBitcoinData(response)
        test.assertValue(response)
    }

    @Test
    fun getRemoteBitcoinData_withDuration_throwsException() {
        whenever(bitcoinChartRemoteDataSource.getRemoteBitcoinData(duration))
            .thenReturn(Single.error(throwable))

        val test = bitcoinRepository.getBitcoinData(duration).test()

        verify(bitcoinChartRemoteDataSource).getRemoteBitcoinData(duration)
        test.assertError(throwable)
    }

    @Test
    fun getLocalBitcoinData_returnsBitcoinData() {
        whenever(reactiveDb.getAllBitcoinData()).thenReturn(Flowable.just(response))

        val test = bitcoinRepository.getLocalBitcoinData().test()

        verify(reactiveDb).getAllBitcoinData()
        test.assertValue(response)
    }

    @Test
    fun getLocalBitcoinData_throwsException() {
        whenever(reactiveDb.getAllBitcoinData())
            .thenReturn(Flowable.error(throwable))

        val test = bitcoinRepository.getLocalBitcoinData().test()

        verify(reactiveDb).getAllBitcoinData()
        test.assertError(throwable)
    }
}
