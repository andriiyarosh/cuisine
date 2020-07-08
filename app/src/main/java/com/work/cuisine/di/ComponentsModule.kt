package com.work.cuisine.di

import com.work.cuisine.MainActivity
import com.work.cuisine.presentation.receipts.info.ReceiptInfoFragment
import com.work.cuisine.presentation.main.MainFragment
import com.work.cuisine.presentation.receipts.random.RandomReceiptFragment
import com.work.cuisine.presentation.receipts.search.SearchReceiptFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ComponentsModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeRandomReceiptFragment(): RandomReceiptFragment

    @ContributesAndroidInjector
    abstract fun contributeSearchReceiptFragment(): SearchReceiptFragment

    @ContributesAndroidInjector
    abstract fun contributeReceiptInfoFragment(): ReceiptInfoFragment
}