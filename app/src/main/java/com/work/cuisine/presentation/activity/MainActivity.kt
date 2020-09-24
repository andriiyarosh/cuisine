package com.work.cuisine.presentation.activity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.work.cuisine.R
import com.work.cuisine.base.BaseActivity
import com.work.cuisine.presentation.activity.NetworkStateObservable.NetworkState

class MainActivity : BaseActivity(), NetworkStateObservable {
    override val layoutRes: Int = R.layout.activity_main

    private val _networkState: MutableLiveData<NetworkState> = MutableLiveData()

    private lateinit var connectivityManager: ConnectivityManager

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onLost(network: Network) {
            _networkState.postValue(NetworkState.Lost)
        }

        override fun onAvailable(network: Network) {
            _networkState.postValue(NetworkState.Active)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun onStart() {
        super.onStart()
        connectivityManager.registerDefaultNetworkCallback(networkCallback)
    }

    override fun onStop() {
        super.onStop()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    override fun subscribe(observer: Observer<NetworkState>) {
        _networkState.observe(this, observer)
    }
}
