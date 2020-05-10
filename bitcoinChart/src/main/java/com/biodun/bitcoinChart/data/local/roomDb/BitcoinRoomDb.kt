package com.biodun.bitcoinChart.data.local.roomDb

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.biodun.bitcoinChart.data.model.BitcoinData

@TypeConverters(BitcoinDataValueTypeConverter::class)
@Database(entities = [BitcoinData::class], version = 1)
abstract class BitcoinRoomDb : RoomDatabase() {
    abstract fun bitcoinDao(): BitcoinDao
}
