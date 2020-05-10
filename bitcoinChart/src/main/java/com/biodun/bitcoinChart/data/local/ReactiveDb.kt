package com.biodun.bitcoinChart.data.local

import io.reactivex.Flowable

interface ReactiveDb<T> {
    fun insertAllBitcoinData(data: T)
    fun getAllBitcoinData(): Flowable<T>
}
