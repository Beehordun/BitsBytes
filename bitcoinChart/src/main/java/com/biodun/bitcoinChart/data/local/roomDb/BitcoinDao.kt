package com.biodun.bitcoinChart.data.local.roomDb

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.biodun.bitcoinChart.data.model.BitcoinData
import com.biodun.core.utils.BITCOIN_TABLE
import io.reactivex.Flowable

@Dao
interface BitcoinDao {

    @Insert
    fun insertAll(bitcoinData: BitcoinData)

    @Query("SELECT * from $BITCOIN_TABLE")
    fun getAll(): Flowable<BitcoinData>
}
