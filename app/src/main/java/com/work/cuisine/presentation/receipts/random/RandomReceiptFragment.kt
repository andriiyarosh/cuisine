package com.work.cuisine.presentation.receipts.random

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.work.cuisine.R
import com.work.cuisine.presentation.receipts.BaseReceiptFragment
import kotlinx.android.synthetic.main.fragment_random_receipt.*
import kotlinx.android.synthetic.main.fragment_search_receipt.progress
import kotlinx.android.synthetic.main.fragment_search_receipt.progressText
import kotlinx.android.synthetic.main.fragment_search_receipt.root

class RandomReceiptFragment : BaseReceiptFragment<RandomReceiptViewModel>(
    R.layout.fragment_random_receipt,
    RandomReceiptViewModel::class.java
) {

    override fun getListAdapter() = list.adapter as RandomListAdapter

    override fun showLoadingView(show: Boolean) {
        TransitionManager.beginDelayedTransition(root)
        progress.isVisible = show
        progressText.isVisible = show
        list.isVisible = !show
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.findRandomReceipts()
    }

    override fun initRecyclerView() {
        with(list) {
            layoutManager = object : LinearLayoutManager(context) {
                override fun canScrollVertically(): Boolean {
                    return false
                }
            }
            adapter = RandomListAdapter(this@RandomReceiptFragment::onReceiptClicked)
        }
    }

    override fun initToolbar() {
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }

    override fun onReceiptClicked(receiptId: Long) =
        findNavController().navigate(RandomReceiptFragmentDirections.randomToInfoAction(receiptId))

    override fun initListeners() {
        next.setOnClickListener { viewModel.findRandomReceipts() }
    }
}