package com.work.cuisine.presentation.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.work.cuisine.R
import com.work.cuisine.base.BaseFragment
import com.work.cuisine.presentation.receipts.BaseReceiptsListAdapter
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment<MainViewModel>(MainViewModel::class.java, R.layout.fragment_main) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initReceiptInfoList()
    }

    override fun initObservers() {
        viewModel.receiptInfo.observe(viewLifecycleOwner, Observer { (list.adapter as BaseReceiptsListAdapter).setItems(it) })
    }

    override fun initRecyclerView() {
        with(list) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = SavedReceiptsListAdapter(this@MainFragment::onReceiptClicked)
        }
    }

    override fun initListeners() {
        searchReceipt.setOnClickListener { findNavController().navigate(MainFragmentDirections.searchFragmentAction()) }
        randomSearch.setOnClickListener { findNavController().navigate(MainFragmentDirections.randomSearchAction()) }
    }

    private fun onReceiptClicked(receiptId: Long) {
        findNavController().navigate(
            MainFragmentDirections.receiptInfoAction(
                receiptId
            )
        )
    }
}