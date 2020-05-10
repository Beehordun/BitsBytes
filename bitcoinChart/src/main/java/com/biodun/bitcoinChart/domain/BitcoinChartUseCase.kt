package com.biodun.bitcoinChart.domain

import com.biodun.bitcoinChart.data.model.BitcoinData
import com.biodun.bitcoinChart.data.repository.BitcoinChartRepository
import com.biodun.core.base.BaseReactiveUseCase
import io.reactivex.Single
import javax.inject.Inject

class BitcoinChartUseCase @Inject constructor(
    private val bitcoinChartRepository: BitcoinChartRepository
) : BaseReactiveUseCase.GetUseCase<String, BitcoinData> {

    override fun getSingle(params: String): Single<BitcoinData> =
        bitcoinChartRepository.getBitcoinData(params)
}
