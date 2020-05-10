package com.biodun.bitcoinChart.data

import com.biodun.bitcoinChart.DummyData
import com.biodun.bitcoinChart.data.remote.BitcoinChartService
import com.biodun.bitcoinChart.data.remote.retrofitRemoteImpl.BitcoinChartRemoteDataSource
import com.biodun.bitcoinChart.data.remote.retrofitRemoteImpl.BitcoinChartRemoteDataSourceImpl
import com.biodun.core.utils.DurationUtil
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class BitcoinChartRemoteDataSourceTest {

    private val bitcoinChartService = mock<BitcoinChartService>()
    private val throwable = Throwable()
    private lateinit var bitcoinChartRemoteDataSource: BitcoinChartRemoteDataSource
    private val queryMap = DummyData.queryMap
    private val dataResponse = DummyData.bitcoinData

    @Before
    fun setUp() {
        bitcoinChartRemoteDataSource = BitcoinChartRemoteDataSourceImpl(bitcoinChartService)
    }

    @Test
    fun getRemoteBitcoinData_withDuration_returnsBitcoinData() {
        whenever(bitcoinChartService.getBitcoinData(queryMap))
            .thenReturn(Single.just(dataResponse))

        bitcoinChartRemoteDataSource.getRemoteBitcoinData(DurationUtil.default)
            .test()
            .run {
                assertNoErrors()
                assertValueCount(1)
                val data = values()[0].values
                Assert.assertEquals(data[0].bitcoinValue, 100.0, 0.0)
            }
    }

    @Test
    fun getRemoteBitcoinData_withDuration_throwsException() {
        whenever(bitcoinChartService.getBitcoinData(queryMap))
            .thenReturn(Single.error(throwable))

        bitcoinChartRemoteDataSource.getRemoteBitcoinData(DurationUtil.default)
            .test()
            .run {
                assertError(throwable)
            }
    }
}
