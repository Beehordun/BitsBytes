package com.biodun.bitcoinChart.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.biodun.bitcoinChart.DummyData
import com.biodun.bitcoinChart.data.local.roomDb.BitcoinDao
import com.biodun.bitcoinChart.data.local.roomDb.BitcoinRoomDb
import com.biodun.bitcoinChart.data.model.BitcoinData
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class RoomReactiveDbTest {
    private lateinit var bitcoinDao: BitcoinDao
    private lateinit var db: BitcoinRoomDb

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, BitcoinRoomDb::class.java).build()
        bitcoinDao = db.bitcoinDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetAllData() {
        val bitcoinData: BitcoinData = DummyData.bitcoinData

        bitcoinDao.insertAll(bitcoinData)

        val returnedData = bitcoinDao.getAll().blockingFirst()
        assertThat(bitcoinData, equalTo(returnedData))
    }
}
