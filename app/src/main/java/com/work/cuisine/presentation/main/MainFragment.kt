package com.work.cuisine.presentation.main

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.work.cuisine.R
import com.work.cuisine.base.BaseFragment
import com.work.cuisine.presentation.activity.NetworkStateObservable
import com.work.cuisine.presentation.receipts.BaseReceiptsListAdapter
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment<MainViewModel>(MainViewModel::class.java, R.layout.fragment_main) {

    private var randomSearchAction: () -> Unit = {}

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.initReceiptInfoList()
    }

    override fun initObservers() {
        viewModel.receiptInfo.observe(viewLifecycleOwner, Observer { (list.adapter as BaseReceiptsListAdapter).setItems(it) })
        getNetworkStateObservable().subscribe(Observer {
            randomSearchAction = when (it) {
                NetworkStateObservable.NetworkState.Active -> {
                    { findNavController().navigate(MainFragmentDirections.randomSearchAction()) }
                }
                NetworkStateObservable.NetworkState.Lost -> {
                    { Snackbar.make(requireView(), getString(R.string.network_connection_lost), Snackbar.LENGTH_SHORT).show() }
                }
            }
        })
    }

    override fun initRecyclerView() {
        with(list) {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = SavedReceiptsListAdapter(this@MainFragment::onReceiptClicked)
        }
    }

    override fun initListeners() {
        searchReceipt.setOnClickListener { findNavController().navigate(MainFragmentDirections.searchFragmentAction()) }
        randomSearch.setOnClickListener { randomSearchAction() }
    }

    private fun onReceiptClicked(receiptId: Long) {
        findNavController().navigate(
            MainFragmentDirections.receiptInfoAction(
                receiptId
            )
        )
    }
}