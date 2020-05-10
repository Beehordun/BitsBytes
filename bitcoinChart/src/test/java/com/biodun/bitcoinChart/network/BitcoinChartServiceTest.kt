package com.biodun.bitcoinChart.network

import com.biodun.bitcoinChart.DummyData
import com.biodun.bitcoinChart.data.remote.BitcoinChartService
import com.biodun.core.model.ErrorResponse
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
    private val queryMap = DummyData.queryMap

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

        @Test
        fun getBitcoinData_WithDefaultDuration_returnsError() {
            queueResponse {
                setResponseCode(400)
                setBody(TestDependencyProvider.getResponseFromJson("bitcoin-error-response"))
            }

            service
                .getBitcoinData(queryMap)
                .test()
                .run {
                    val httpException = errors()[0] as HttpException
                    val errorBody = httpException.response().errorBody()?.string()
                    val errorDataResponse: ErrorResponse =
                        Gson().fromJson(errorBody, ErrorResponse::class.java)
                    Assert.assertEquals(errorDataResponse.status, "not-found")
                    Assert.assertNull(errorDataResponse.data)
                }
        }

    private fun queueResponse(block: MockResponse.() -> Unit) {
        mockWebServer.enqueue(MockResponse().apply(block))
    }
}
