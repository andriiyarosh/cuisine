package com.work.cuisine.presentation.receipts.info

import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.work.cuisine.R
import com.work.cuisine.base.BaseFragment
import com.work.cuisine.network.moodels.receipt.Receipt
import kotlinx.android.synthetic.main.fragment_receipt_info.*

class ReceiptInfoFragment : BaseFragment<ReceiptInfoViewModel>(
    ReceiptInfoViewModel::class.java,
    R.layout.fragment_receipt_info
) {

    private val args: ReceiptInfoFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getReceiptInfo(args.receiptId)
    }

    override fun initRecyclerView() {
        with(list) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = InstructionListAdapter()
        }
    }

    override fun initToolbar() {
        toolbar.setNavigationOnClickListener { findNavController().popBackStack() }
    }

    override fun initListeners() {
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.actionDelete -> {
                    viewModel.deleteReceipt()
                    it.isVisible = false
                    toolbar.menu.findItem(R.id.actionSave).isVisible = true
                }
                R.id.actionSave -> {
                    viewModel.saveReceipt()
                    it.isVisible = false
                    toolbar.menu.findItem(R.id.actionDelete).isVisible = true
                }
            }
            return@setOnMenuItemClickListener true
        }
    }

    override fun initObservers() {
        viewModel.receiptInfo.observe(viewLifecycleOwner, Observer { handleLoadingStates(it) })
    }

    private fun handleLoadingStates(loadingStates: ReceiptInfoViewModel.InitializingSteps) {
        when (loadingStates) {
            ReceiptInfoViewModel.InitializingSteps.StartLoading -> showLoadingView(true)
            is ReceiptInfoViewModel.InitializingSteps.FinishedLoading -> {
                val receipt = loadingStates.receiptInfo.first
                if (receipt != null)
                    initData(receipt)
                val instruction = loadingStates.receiptInfo.second
                if (instruction != null)
                    (list.adapter as InstructionListAdapter).setItems(instruction.steps)
                showLoadingView(false)
            }
        }
    }

    private fun showLoadingView(loading: Boolean) {
        content.isVisible = !loading
        progress.isVisible = loading
        progressText.isVisible = loading
    }

    private fun initData(receipt: Receipt) {
        Glide.with(requireView()).load(receipt.image).into(image)
        title.text = receipt.title
        servings.text = getString(R.string.servings, receipt.servings)
        readyIn.text = getString(R.string.ready_in, receipt.readyInMinutes)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            summary.text = Html.fromHtml(receipt.summary, Html.FROM_HTML_MODE_COMPACT);
        else
            summary.text = Html.fromHtml(receipt.summary)
        toolbar.menu.findItem(R.id.actionDelete).isVisible = receipt.isSaved
        toolbar.menu.findItem(R.id.actionSave).isVisible = !receipt.isSaved
    }
}