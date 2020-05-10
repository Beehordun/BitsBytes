package com.biodun.core.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [CoreModule::class])
interface CoreComponent {

    val coreSubComponent: CoreSubComponent


    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: Application): CoreComponent
    }
}
