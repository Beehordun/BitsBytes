package com.biodun.bitcoinChart.presentation

import androidx.lifecycle.MutableLiveData
import com.biodun.bitcoinChart.data.model.BitcoinData
import com.biodun.core.base.BaseReactiveUseCase
import com.biodun.core.base.BaseViewModel
import com.biodun.core.extentions.setError
import com.biodun.core.extentions.setLoading
import com.biodun.core.extentions.setSuccess
import com.biodun.core.scheduler.SchedulerInterface
import com.biodun.core.stateManagement.Result
import javax.inject.Inject

class BitcoinChartViewModel @Inject constructor(
    private val bitcoinChartUseCase:
    BaseReactiveUseCase.GetUseCase<String, BitcoinData>,
    private val appScheduler: SchedulerInterface
) : BaseViewModel() {

    val bitcoinChartLiveData = MutableLiveData<Result<BitcoinData>>()

    fun getBitcoinData(duration: String) {
        addDisposable(
            bitcoinChartUseCase.getSingle(duration)
                .doOnSubscribe { bitcoinChartLiveData.setLoading() }
                .subscribeOn(appScheduler.io())
                .observeOn(appScheduler.mainThread())
                .subscribe(
                    {
                        bitcoinChartLiveData.setSuccess(it)
                    },
                    {
                        bitcoinChartLiveData.setError(it as Exception)
                    })
        )
    }
}
