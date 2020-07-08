package com.work.cuisine.presentation.receipts.search

import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.transition.TransitionManager
import com.work.cuisine.R
import com.work.cuisine.presentation.receipts.BaseReceiptFragment
import kotlinx.android.synthetic.main.fragment_search_receipt.*

class SearchReceiptFragment : BaseReceiptFragment<ReceiptViewModel>(R.layout.fragment_search_receipt, ReceiptViewModel::class.java) {

    override fun getListAdapter() = list.adapter as SearchReceiptListAdapter

    override fun initRecyclerView() {
        with(list) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = SearchReceiptListAdapter(this@SearchReceiptFragment::onReceiptClicked)
        }
    }

    override fun initToolbar() {
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
        val findItem = toolbar.menu.findItem(R.id.actionSearch)
        val searchView = findItem.actionView as SearchView
        searchView
            .setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    query?.let { viewModel.findReceipts(it) }
                    if( ! searchView.isIconified) {
                        searchView.isIconified = true
                    }
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

    override fun onReceiptClicked(receiptId: Long) {
        findNavController().navigate(SearchReceiptFragmentDirections.searchToInfoAction(receiptId))
    }
}