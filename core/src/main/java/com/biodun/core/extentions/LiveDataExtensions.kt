package com.biodun.core.extentions

import androidx.lifecycle.MutableLiveData
import com.biodun.core.stateManagement.ResultState
import com.biodun.core.stateManagement.Result as Result

fun <T> MutableLiveData<Result<T>>.setLoading() =
    postValue(Result(ResultState.LOADING, value?.data))

fun <T> MutableLiveData<Result<T>>.setSuccess(data: T) =
    postValue(Result(ResultState.SUCCESS, data))

fun <T> MutableLiveData<Result<T>>.setError(exception: Exception) =
    postValue(Result(ResultState.ERROR, value?.data, exception))
