package com.biodun.blockchainmarket.data.remote.retrofitRemoteImpl

import com.biodun.blockchainmarket.data.model.BitcoinData
import com.biodun.blockchainmarket.data.remote.BitcoinChartRemoteDataSource
import com.biodun.core.utils.QueryMapper.getQueryMap
import io.reactivex.Single
import javax.inject.Inject

class BitcoinChartRemoteDataSourceImpl @Inject constructor(
    private val bitcoinChartService: BitcoinChartService
) : BitcoinChartRemoteDataSource {

    override fun getRemoteBitcoinData(duration: String): Single<BitcoinData> =
        bitcoinChartService.getBitcoinData(getQueryMap(duration))
}
