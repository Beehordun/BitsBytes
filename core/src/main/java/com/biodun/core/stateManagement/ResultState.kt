package com.biodun.core.stateManagement

sealed class ResultState {
    object LOADING : ResultState()
    object SUCCESS : ResultState()
    object ERROR : ResultState()
}
