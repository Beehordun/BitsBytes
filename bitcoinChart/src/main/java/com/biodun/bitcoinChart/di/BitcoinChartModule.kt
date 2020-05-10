package com.biodun.bitcoinChart.di

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import com.biodun.bitcoinChart.data.local.roomDb.BitcoinRoomDb
import com.biodun.bitcoinChart.data.local.ReactiveDb
import com.biodun.bitcoinChart.data.local.roomDb.RoomReactiveDb
import com.biodun.bitcoinChart.data.model.BitcoinData
import com.biodun.bitcoinChart.data.remote.BitcoinChartService
import com.biodun.bitcoinChart.data.remote.retrofitRemoteImpl.BitcoinChartRemoteDataSource
import com.biodun.bitcoinChart.data.remote.retrofitRemoteImpl.BitcoinChartRemoteDataSourceImpl
import com.biodun.bitcoinChart.data.repository.BitcoinChartRepository
import com.biodun.bitcoinChart.data.repository.BitcoinChartRepositoryImpl
import com.biodun.bitcoinChart.domain.BitcoinChartUseCase
import com.biodun.core.base.BaseReactiveUseCase
import com.biodun.core.scheduler.AppScheduler
import com.biodun.core.scheduler.SchedulerInterface
import com.biodun.core.utils.DATABASE_NAME
import dagger.Binds
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
interface BitcoinChartModule {

    @Binds
    fun provideRoomReactiveDb(roomReactiveDb: RoomReactiveDb): ReactiveDb<BitcoinData>

    @Binds
    fun provideBitcoinChartRemoteDataSource(
        bitcoinChartRemoteDataSourceImpl: BitcoinChartRemoteDataSourceImpl
    ): BitcoinChartRemoteDataSource

    @Binds
    fun provideBitcoinChartRepository(
        bitcoinChartRepositoryImpl: BitcoinChartRepositoryImpl
    ): BitcoinChartRepository

    @Binds
    fun provideBitcoinChartUseCase(
        bitcoinChartUseCase: BitcoinChartUseCase
    ): BaseReactiveUseCase.GetUseCase<String, BitcoinData>

    companion object {

        @Provides
        @Singleton
        fun provideBitcoinDatabase(applicationContext: Application): BitcoinRoomDb {
            return Room.databaseBuilder(
                applicationContext,
                BitcoinRoomDb::class.java, DATABASE_NAME
            ).build()
        }

        @Provides
        @Singleton
        fun provideBitcoinChartService(retrofit: Retrofit): BitcoinChartService =
            retrofit.create(BitcoinChartService::class.java)

    }
}
