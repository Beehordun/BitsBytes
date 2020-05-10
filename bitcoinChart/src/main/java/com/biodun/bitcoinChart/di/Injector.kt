package com.biodun.bitcoinChart.di

import com.biodun.bitcoinChart.ui.BitcoinChartActivity
import com.biodun.blockchainmarket.BlockChainMarketApp

fun BitcoinChartActivity.inject() {
    val coreComponent = BlockChainMarketApp.coreComponent(this)
    DaggerBitcoinChartComponent.factory().create(coreComponent).inject(this)
}
