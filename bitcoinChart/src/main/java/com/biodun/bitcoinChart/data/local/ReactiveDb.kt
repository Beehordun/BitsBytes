package com.biodun.bitcoinChart.data.local

import io.reactivex.Flowable

interface ReactiveDb<T> {
    fun insertAllBitcoinData(data: List<T>)
    fun getAllBitcoinData(): Flowable<List<T>>
}
