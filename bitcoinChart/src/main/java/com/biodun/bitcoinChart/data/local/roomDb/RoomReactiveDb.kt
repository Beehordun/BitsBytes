package com.biodun.bitcoinChart.data.local.roomDb

import com.biodun.bitcoinChart.data.local.ReactiveDb
import com.biodun.bitcoinChart.data.model.BitcoinData
import io.reactivex.Flowable
import javax.inject.Inject

class RoomReactiveDb @Inject constructor(private val bitcoinRoomDb: BitcoinRoomDb) :
    ReactiveDb<BitcoinData> {

    override fun insertAllBitcoinData(data: BitcoinData) {
        bitcoinRoomDb.bitcoinDao().insertAll(data)
    }

    override fun getAllBitcoinData(): Flowable<BitcoinData> =
        bitcoinRoomDb.bitcoinDao().getAll()
}
