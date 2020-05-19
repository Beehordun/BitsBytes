package com.biodun.bitcoinChart.domain

import com.biodun.bitcoinChart.DummyData
import com.biodun.bitcoinChart.data.repository.BitcoinChartRepository
import com.biodun.core.utils.DurationUtil
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class BitcoinChartUseCaseTest {

    private lateinit var bitcoinChartUseCase: BitcoinChartUseCase
    private val repository: BitcoinChartRepository = mock()
    private val duration = DurationUtil.default
    private val bitcoinResponse = DummyData.bitcoinData

    @Before
    fun setUp() {
        bitcoinChartUseCase = BitcoinChartUseCase(repository)
    }

    @Test
    fun getSingle_withDuration_returnsBitcoinDataResponse() {
        whenever(repository.getBitcoinData(duration))
            .thenReturn(Single.just(bitcoinResponse))

        val test = bitcoinChartUseCase.getSingle(duration).test()

        verify(repository).getBitcoinData(duration)

        test.run {
            assertNoErrors()
            assertComplete()
            assertValueCount(1)
            val response = test.values()[0]
            Assert.assertNotNull(response)
            Assert.assertNotNull(response.values)
            Assert.assertEquals(response, bitcoinResponse)
        }
    }

    @Test
    fun getSingle_withDuration_callsGetLocalBitcoinData_whenException() {
        val throwable = Throwable()

        whenever(repository.getBitcoinData(duration)).thenReturn(Single.error(throwable))
        whenever(repository.getLocalBitcoinData()).thenReturn(Flowable.just(bitcoinResponse))

        val test = bitcoinChartUseCase.getSingle(duration).test()

        verify(repository).getBitcoinData(duration)
        verify(repository).getLocalBitcoinData()

        test.run {
            assertNoErrors()
            assertComplete()
            assertValueCount(1)
            val response = test.values()[0]
            Assert.assertNotNull(response)
            Assert.assertNotNull(response.values)
            Assert.assertEquals(response, bitcoinResponse)
        }
    }

    @Test
    fun getSingle_withDuration_throwsException() {
        val throwable = Throwable()

        whenever(repository.getBitcoinData(duration)).thenReturn(Single.error(throwable))
        whenever(repository.getLocalBitcoinData()).thenReturn(Flowable.error(throwable))

        val test = bitcoinChartUseCase.getSingle(duration).test()

        verify(repository).getBitcoinData(duration)
        verify(repository).getLocalBitcoinData()

        test.run {
            assertError(throwable)
        }
    }
}
