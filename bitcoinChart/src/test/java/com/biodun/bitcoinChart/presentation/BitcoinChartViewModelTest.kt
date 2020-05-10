package com.biodun.bitcoinChart.presentation

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.biodun.bitcoinChart.DummyData
import com.biodun.bitcoinChart.data.model.BitcoinData
import com.biodun.core.base.BaseReactiveUseCase
import com.biodun.core.stateManagement.Result
import com.biodun.core.stateManagement.ResultState
import com.biodun.core.utils.DurationUtil
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import com.example.testshared.TestScheduler as LocalTestScheduler

class BitcoinChartViewModelTest {
    private lateinit var bitcoinChartViewModel: BitcoinChartViewModel
    private val duration = DurationUtil.default
    private val bitcoinDataResponse = DummyData.bitcoinData

    private val bitcoinChartUseCase:
            BaseReactiveUseCase.GetUseCase<String, BitcoinData> = mock()

    @Rule
    @JvmField
    val instantTaskExecutorRule: TestRule = InstantTaskExecutorRule()
    private val testScheduler = TestScheduler()

    @Before
    fun setUp() {
        bitcoinChartViewModel = BitcoinChartViewModel(bitcoinChartUseCase, LocalTestScheduler())
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }
    }

    @Test
    fun getBitcoinData_withDuration_returnsBitcoinData() {
        val singleVerifyUserResponse = Single.just(bitcoinDataResponse)

        whenever(bitcoinChartUseCase.getSingle(duration))
            .thenReturn(singleVerifyUserResponse)

        bitcoinChartViewModel.getBitcoinData(duration)

        verify(bitcoinChartUseCase).getSingle(duration)
        Assert.assertEquals(
            Result(state = ResultState.SUCCESS, data = bitcoinDataResponse),
            bitcoinChartViewModel.bitcoinChartLiveData.value
        )
    }

    @Test
    fun getBitcoinData_withDuration_throwsException() {
        val exception = Exception()
        whenever(bitcoinChartUseCase.getSingle(duration))
            .thenReturn(Single.error(exception))

        bitcoinChartViewModel.getBitcoinData(duration)

        verify(bitcoinChartUseCase).getSingle(duration)
        Assert.assertEquals(
            exception,
            bitcoinChartViewModel.bitcoinChartLiveData.value?.exception
        )
    }
}
