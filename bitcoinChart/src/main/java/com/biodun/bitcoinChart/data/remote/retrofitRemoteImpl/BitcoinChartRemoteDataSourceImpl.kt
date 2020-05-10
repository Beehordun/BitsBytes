package com.biodun.bitcoinChart.data.remote.retrofitRemoteImpl

import com.biodun.bitcoinChart.data.model.BitcoinData
import com.biodun.bitcoinChart.data.remote.BitcoinChartService
import com.biodun.core.utils.QueryMapper
import com.biodun.core.utils.QueryMapper.getQueryMap
import io.reactivex.Single
import javax.inject.Inject

class BitcoinChartRemoteDataSourceImpl @Inject constructor(
    private val bitcoinChartService: BitcoinChartService
) : BitcoinChartRemoteDataSource {

    override fun getRemoteBitcoinData(duration: String): Single<BitcoinData> =
        bitcoinChartService.getBitcoinData(getQueryMap(duration))
}
