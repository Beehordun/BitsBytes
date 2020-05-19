package com.biodun.blockchainmarket.di

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.biodun.blockchainmarket.data.local.roomDb.BitcoinRoomDb
import com.biodun.blockchainmarket.data.local.ReactiveDb
import com.biodun.blockchainmarket.data.local.roomDb.RoomReactiveDb
import com.biodun.blockchainmarket.data.model.BitcoinData
import com.biodun.blockchainmarket.data.remote.retrofitRemoteImpl.BitcoinChartService
import com.biodun.blockchainmarket.data.remote.BitcoinChartRemoteDataSource
import com.biodun.blockchainmarket.data.remote.retrofitRemoteImpl.BitcoinChartRemoteDataSourceImpl
import com.biodun.blockchainmarket.data.repository.BitcoinChartRepository
import com.biodun.blockchainmarket.data.repository.BitcoinChartRepositoryImpl
import com.biodun.blockchainmarket.domain.BitcoinChartUseCase
import com.biodun.blockchainmarket.presentation.BitcoinChartViewModel
import com.biodun.core.base.BaseReactiveUseCase
import com.biodun.core.di.ViewModelKey
import com.biodun.core.utils.DATABASE_NAME
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
interface BitcoinChartModule {

    @Binds
    fun bindRoomReactiveDb(roomReactiveDb: RoomReactiveDb): ReactiveDb<BitcoinData>

    @Binds
    fun bindBitcoinChartRemoteDataSource(
        bitcoinChartRemoteDataSourceImpl: BitcoinChartRemoteDataSourceImpl
    ): BitcoinChartRemoteDataSource

    @Binds
    fun bindBitcoinChartRepository(
        bitcoinChartRepositoryImpl: BitcoinChartRepositoryImpl
    ): BitcoinChartRepository

    @Binds
    fun bindBitcoinChartUseCase(
        bitcoinChartUseCase: BitcoinChartUseCase
    ): BaseReactiveUseCase.GetUseCase<String, BitcoinData>

    @Binds
    fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(BitcoinChartViewModel::class)
    fun bindBitcoinViewModel(bitcoinChartViewModel: BitcoinChartViewModel): ViewModel

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
