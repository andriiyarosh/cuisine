package com.work.cuisine.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.work.cuisine.R
import com.work.cuisine.network.moodels.receipt.Receipt
import com.work.cuisine.presentation.receipts.BaseReceiptsListAdapter
import kotlinx.android.synthetic.main.item_short_receipt.view.*

class SavedReceiptsListAdapter(private val onItemClicked: (receiptId: Long) -> Unit) : BaseReceiptsListAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        LayoutInflater.from(parent.context).inflate(
            R.layout.item_short_receipt, parent, false
        )
    )

    override fun getItemCount() = receipts.size

    inner class ViewHolder(itemView: View) : ReceiptViewHolder(itemView) {

        override fun bind(receipt: Receipt) {
            with(itemView) {
                setOnClickListener { onItemClicked(receipt.id) }
                Glide.with(itemView).load(receipt.image).into(image)
                title.text = receipt.title
            }
        }
    }
}