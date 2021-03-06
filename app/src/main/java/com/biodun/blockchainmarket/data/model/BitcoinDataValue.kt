package com.biodun.blockchainmarket.data.model

import com.google.gson.annotations.SerializedName

data class BitcoinDataValue(
    @SerializedName("x") val time: Long,
    @SerializedName("y") val bitcoinValue: Double
)
