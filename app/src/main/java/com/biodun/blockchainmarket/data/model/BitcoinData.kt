package com.biodun.blockchainmarket.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.biodun.core.utils.BITCOIN_TABLE

@Entity(tableName = BITCOIN_TABLE)
data class BitcoinData(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val description: String,
    val values: List<BitcoinDataValue>
)
