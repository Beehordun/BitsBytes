package com.biodun.blockchainmarket.domain

import com.biodun.blockchainmarket.data.model.BitcoinData
import com.biodun.blockchainmarket.data.repository.BitcoinChartRepository
import com.biodun.core.base.BaseReactiveUseCase
import io.reactivex.Single
import javax.inject.Inject

class BitcoinChartUseCase @Inject constructor(
    private val bitcoinChartRepository: BitcoinChartRepository
) : BaseReactiveUseCase.GetUseCase<String, BitcoinData> {

    override fun getSingle(params: String): Single<BitcoinData> =
        getFromDbWhenRemoteIsEmpty(params)

    private fun getFromDbWhenRemoteIsEmpty(params: String): Single<BitcoinData> =
        bitcoinChartRepository.getBitcoinData(params).onErrorResumeNext {
            bitcoinChartRepository.getLocalBitcoinData().firstOrError()
        }
}
