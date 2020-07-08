package com.work.cuisine.di

import android.content.Context
import com.work.cuisine.db.CuisineDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(context: Context): CuisineDatabase = CuisineDatabase.instance(context)

    @Provides
    fun provideDao(database: CuisineDatabase) = database.getReceiptDao()
}