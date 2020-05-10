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
import io.reactivex.Single
import org.junit.Before
import org.junit.Test

class BitcoinChartRepositoryTest {

    private lateinit var bitcoinRepository: BitcoinChartRepository
    private val bitcoinChartRemoteDataSource: BitcoinChartRemoteDataSource = mock()
    private val reactiveDb: ReactiveDb<BitcoinData> = mock()
    private val response = DummyData.bitcoinData
    private val duration = DurationUtil.default
    private val throwable = Throwable()

    @Before
    fun setUp() {
        bitcoinRepository = BitcoinChartRepositoryImpl(bitcoinChartRemoteDataSource, reactiveDb)
    }

    @Test
    fun getRemoteBitcoinData_withDuration_returnsBitcoinData() {
        whenever(bitcoinChartRemoteDataSource.getRemoteBitcoinData(duration))
            .thenReturn(Single.just(response))
        val test = bitcoinRepository.getBitcoinData(duration).test()
        verify(bitcoinChartRemoteDataSource).getRemoteBitcoinData(duration)
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
}
