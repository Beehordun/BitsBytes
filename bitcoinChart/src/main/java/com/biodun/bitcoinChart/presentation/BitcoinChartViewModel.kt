package com.biodun.bitcoinChart.presentation

import android.widget.Toast
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
                .doOnSubscribe { bitcoinChartLiveData.setLoading()
                println("Debug:   Called")}
                .subscribeOn(appScheduler.io())
                .observeOn(appScheduler.mainThread())
                .subscribe(
                    {
                        println("Debug: Sizeeee: ${it.values.size}")
                        bitcoinChartLiveData.setSuccess(it)
                    },
                    {
                        println("Debug: Error!! --> ${it.message}")
                        bitcoinChartLiveData.setError(it as Exception)
                    })
        )
    }
}
