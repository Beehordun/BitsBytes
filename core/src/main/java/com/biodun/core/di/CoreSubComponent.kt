package com.biodun.core.di

import android.app.Application
import com.biodun.core.scheduler.SchedulerInterface
import dagger.Subcomponent
import retrofit2.Retrofit

@Subcomponent
interface CoreSubComponent {
    fun scheduler(): SchedulerInterface
    fun retrofit(): Retrofit
    val application: Application
}
