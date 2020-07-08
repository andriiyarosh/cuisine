package com.work.cuisine.base

import android.os.Bundle
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity: DaggerAppCompatActivity() {

    abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        initObservers()
        initListeners()
    }

    protected open fun initObservers() = Unit
    protected open fun initListeners() = Unit
}