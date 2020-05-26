package com.biodun.bitcoinChart

import com.biodun.blockchainmarket.data.model.BitcoinData
import com.biodun.blockchainmarket.data.model.BitcoinDataValue
import com.biodun.core.utils.Duration.DEFAULT
import com.biodun.core.utils.QueryMapper

object DummyData {

    val queryMap = QueryMapper.getQueryMap(DEFAULT.time)
    private val bitcoinDataValue = BitcoinDataValue(time = 1234, bitcoinValue = 100.0)

    val bitcoinData = BitcoinData(
        id = 1,
        name = "Test",
        description = "Test graph",
        values = listOf(bitcoinDataValue)
    )
}
