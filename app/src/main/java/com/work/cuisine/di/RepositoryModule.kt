package com.work.cuisine.di

import com.work.cuisine.repository.ReceiptsRepository
import com.work.cuisine.repository.ReceiptsRepositoryImpl
import dagger.Binds
import dagger.Module

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun bindReceiptsRepository(receiptsRepository: ReceiptsRepositoryImpl): ReceiptsRepository
}