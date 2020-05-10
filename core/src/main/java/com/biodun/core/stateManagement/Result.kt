package com.biodun.core.stateManagement

data class Result<out T> constructor(
    val state: ResultState,
    val data: T? = null,
    val exception: Exception? = null
)
