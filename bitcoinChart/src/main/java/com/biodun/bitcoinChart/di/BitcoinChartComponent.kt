package com.biodun.bitcoinChart.di

import com.biodun.bitcoinChart.ui.BitcoinChartActivity
import com.biodun.core.di.CoreSubComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [BitcoinChartModule::class], dependencies = [CoreSubComponent::class])
interface BitcoinChartComponent {

    fun inject(bitcoinChartActivity: BitcoinChartActivity)

    @Component.Factory
    interface Factory {
        fun create(coreComponent: CoreSubComponent): BitcoinChartComponent
    }
}
