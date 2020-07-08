package com.work.cuisine.di

import com.work.cuisine.Application

object AppComponentProvider {
    lateinit var appComponent: AppComponent
    fun provideAppComponent(context: Application) =
        if (this::appComponent.isInitialized) appComponent
        else {
            appComponent = DaggerAppComponent.factory().create(context)
            appComponent

        }
}