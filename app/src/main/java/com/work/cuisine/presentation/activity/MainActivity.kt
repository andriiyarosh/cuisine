package com.work.cuisine.presentation.activity

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
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

    override fun onResume() {
        super.onResume()
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
    }

    override fun onStop() {
        super.onStop()
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    @SuppressLint("RestrictedApi")
    override fun onBackPressed() {
        if (findNavController(R.id.hostFragment).previousBackStackEntry == null)
            return
        super.onBackPressed()
    }

    override fun subscribe(observer: Observer<NetworkState>) {
        _networkState.observe(this, observer)
    }
}
