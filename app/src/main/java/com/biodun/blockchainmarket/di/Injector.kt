package com.biodun.blockchainmarket.di

import com.biodun.blockchainmarket.BlockChainMarketApp
import com.biodun.blockchainmarket.ui.BitcoinChartActivity

fun BitcoinChartActivity.inject() {
    val coreSubComponent = BlockChainMarketApp.coreSubComponent(this)
    DaggerBitcoinChartComponent.factory().create(coreSubComponent).inject(this)
}
