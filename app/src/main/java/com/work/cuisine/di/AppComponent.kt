package com.work.cuisine.di

import android.content.Context
import com.work.cuisine.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Component(
    modules = [
        AndroidInjectionModule::class,
        ViewModelModule::class,
        ComponentsModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        DatabaseModule::class]
)
@Singleton
interface AppComponent : AndroidInjector<Application> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}