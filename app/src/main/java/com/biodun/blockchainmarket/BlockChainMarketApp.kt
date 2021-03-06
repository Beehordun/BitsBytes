package com.biodun.blockchainmarket

import android.app.Application
import android.content.Context
import com.biodun.core.di.CoreComponent
import com.biodun.core.di.DaggerCoreComponent

class BlockChainMarketApp : Application() {

    private val coreComponent: CoreComponent by lazy {
        DaggerCoreComponent.factory().create(this)
    }

    companion object {
        @JvmStatic
        fun coreSubComponent(context: Context) =
            (context.applicationContext as BlockChainMarketApp).coreComponent.coreSubComponent
    }
}
