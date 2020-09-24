package com.work.cuisine.presentation.activity

import androidx.lifecycle.Observer

interface NetworkStateObservable {

    fun subscribe(observer: Observer<NetworkState>)

    sealed class NetworkState {
        object Active: NetworkState()
        object Lost: NetworkState()
    }
}