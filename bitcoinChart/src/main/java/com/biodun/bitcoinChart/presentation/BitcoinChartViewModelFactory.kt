package com.biodun.bitcoinChart.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.biodun.bitcoinChart.data.model.BitcoinData
import com.biodun.core.base.BaseReactiveUseCase
import com.biodun.core.scheduler.SchedulerInterface
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class BitcoinChartViewModelFactory @Inject constructor(
    private val scheduler: SchedulerInterface,
    private val bitcoinChartUseCase: BaseReactiveUseCase.GetUseCase<String, BitcoinData>
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        BitcoinChartViewModel(bitcoinChartUseCase, scheduler) as T
}
