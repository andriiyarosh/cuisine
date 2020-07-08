package com.work.cuisine.presentation.receipts

import androidx.annotation.LayoutRes
import androidx.lifecycle.Observer
import com.work.cuisine.base.BaseFragment

abstract class BaseReceiptFragment<VM : BaseReceiptViewModel>(@LayoutRes private val layoutResId: Int, clazz: Class<VM>):
    BaseFragment<VM>(clazz, layoutResId) {

    abstract fun onReceiptClicked(receiptId: Long)
    abstract fun getListAdapter(): BaseReceiptsListAdapter
    abstract fun showLoadingView(show: Boolean)

    override fun initObservers() {
        viewModel.receipts.observe(viewLifecycleOwner, Observer { processRequestResult(it) })
    }

    private fun processRequestResult(result: BaseReceiptViewModel.RequestSteps) {
        when(result) {
            BaseReceiptViewModel.RequestSteps.StartRequest -> showLoadingView(true)
            is BaseReceiptViewModel.RequestSteps.RequestFinished -> {
                getListAdapter().setItems(result.receipts)
                showLoadingView(false)
            }
        }
    }
}