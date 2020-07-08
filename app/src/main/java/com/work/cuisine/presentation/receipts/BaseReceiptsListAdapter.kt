package com.work.cuisine.presentation.receipts

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.work.cuisine.network.moodels.receipt.Receipt

abstract class BaseReceiptsListAdapter : RecyclerView.Adapter<BaseReceiptsListAdapter.ReceiptViewHolder>() {

    protected var receipts: List<Receipt> = emptyList()

    override fun onBindViewHolder(holder: ReceiptViewHolder, position: Int) {
        holder.bind(receipts[position])
    }

    fun setItems(receipts: List<Receipt>) {
        this.receipts = receipts
        notifyDataSetChanged()
    }

    abstract class ReceiptViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        abstract fun bind(receipt: Receipt)
    }
}