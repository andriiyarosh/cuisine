package com.work.cuisine.presentation.receipts.search

import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.google.android.material.snackbar.Snackbar
import com.work.cuisine.R
import com.work.cuisine.presentation.activity.NetworkStateObservable
import com.work.cuisine.presentation.receipts.BaseReceiptFragment
import kotlinx.android.synthetic.main.fragment_search_receipt.*

class SearchReceiptFragment : BaseReceiptFragment<ReceiptViewModel>(
    R.layout.fragment_search_receipt,
    ReceiptViewModel::class.java
) {

    private var isNetworkAvailable: Boolean = false

    override fun getListAdapter() = list.adapter as SearchReceiptListAdapter

    override fun initRecyclerView() {
        with(list) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = SearchReceiptListAdapter(this@SearchReceiptFragment::onReceiptClicked)
        }
    }

    override fun initObservers() {
        super.initObservers()
        getNetworkStateObservable().subscribe(Observer {
            isNetworkAvailable = when (it) {
                NetworkStateObservable.NetworkState.Active -> true
                NetworkStateObservable.NetworkState.Lost -> false
            }
        })
    }

    override fun initToolbar() {
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        val findItem = toolbar.menu.findItem(R.id.actionSearch)
        val searchView = findItem.actionView as SearchView
        searchView
            .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (isNetworkAvailable)
                        query?.let { viewModel.findReceipts(it) }
                    else
                        Snackbar.make(requireView(), getString(R.string.network_connection_lost), Snackbar.LENGTH_SHORT).show()

                    if (!searchView.isIconified)
                        searchView.isIconified = true

                    findItem.collapseActionView()
                    return true
                }

                override fun onQueryTextChange(newText: String?) = false
            })
    }

    override fun showLoadingView(show: Boolean) {
        TransitionManager.beginDelayedTransition(root)
        progress.isVisible = show
        progressText.isVisible = show
        list.isVisible = !show
    }

    override fun onReceiptClicked(receiptId: Long) =
        findNavController().navigate(SearchReceiptFragmentDirections.searchToInfoAction(receiptId))
}