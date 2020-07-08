package com.work.cuisine.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerFragment
import javax.inject.Inject

open class BaseFragment<VM : ViewModel>(clazz: Class<VM>, @LayoutRes private val layoutResId: Int) :
    DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected val viewModel: VM by lazy { ViewModelProvider(viewModelStore, viewModelFactory)[clazz] }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(layoutResId, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initObservers()
        initToolbar()
        initListeners()
        initRecyclerView()
    }

    protected open fun initObservers() = Unit
    protected open fun initListeners() = Unit
    protected open fun initToolbar() = Unit
    protected open fun initRecyclerView() = Unit
}

