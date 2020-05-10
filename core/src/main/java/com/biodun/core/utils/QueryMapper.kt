package com.biodun.core.utils

object QueryMapper {

    fun getQueryMap(duration: String): Map<String, String> =
        mutableMapOf("timespan" to duration, "rollingAverage" to "8hours", "format" to "json")
}
