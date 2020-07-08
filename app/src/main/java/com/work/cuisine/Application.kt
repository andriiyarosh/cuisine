package com.work.cuisine

import com.work.cuisine.di.AppComponent
import com.work.cuisine.di.AppComponentProvider
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class Application: DaggerApplication() {

    private lateinit var appComponent: AppComponent

    override fun onCreate() {
        appComponent = AppComponentProvider.provideAppComponent(this)
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return appComponent
    }
}