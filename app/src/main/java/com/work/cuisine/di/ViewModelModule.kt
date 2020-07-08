package com.work.cuisine.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.work.cuisine.presentation.main.MainViewModel
import com.work.cuisine.presentation.receipts.info.ReceiptInfoViewModel
import com.work.cuisine.presentation.receipts.search.ReceiptViewModel
import com.work.cuisine.presentation.receipts.random.RandomReceiptViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(ReceiptInfoViewModel::class)
    abstract fun bindReceiptInfoViewModel(viewModel: ReceiptInfoViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReceiptViewModel::class)
    abstract fun bindSearchReceiptViewModel(viewModel: ReceiptViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RandomReceiptViewModel::class)
    abstract fun bindRandomReceiptViewModel(viewModel: RandomReceiptViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}