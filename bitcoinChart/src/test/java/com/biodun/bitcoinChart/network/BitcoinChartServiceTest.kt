package com.biodun.bitcoinChart.network

import com.biodun.bitcoinChart.data.remote.BitcoinChartService
import com.biodun.core.utils.DurationUtil
import com.biodun.core.utils.QueryMapper
import com.example.testshared.TestDependencyProvider
import com.google.gson.Gson
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class BitcoinChartServiceTest {

    private lateinit var service: BitcoinChartService
    private lateinit var mockWebServer: MockWebServer
    private val queryMap = QueryMapper.getQueryMap(DurationUtil.default)

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        service = TestDependencyProvider
            .getRetrofit(mockWebServer.url("/"))
            .create(BitcoinChartService::class.java)
    }

    @After
    @Throws(IOException::class)
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun getBitcoinData_withDefaultDuration_returnsBitcoinData() {
        queueResponse {
            setResponseCode(200)
            setBody(TestDependencyProvider.getResponseFromJson("bitcoin-success-response"))
        }

        service
            .getBitcoinData(queryMap)
            .test()
            .run {
                assertNoErrors()
                assertValueCount(1)
                Assert.assertEquals(values()[0].values[0].bitcoinValue, 188330.0, 0.00)
            }
    }

    private fun queueResponse(block: MockResponse.() -> Unit) {
        mockWebServer.enqueue(MockResponse().apply(block))
    }

}