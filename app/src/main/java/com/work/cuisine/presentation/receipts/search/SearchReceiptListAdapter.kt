package com.work.cuisine.presentation.receipts.search

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.work.cuisine.R
import com.work.cuisine.network.moodels.receipt.Receipt
import com.work.cuisine.presentation.receipts.BaseReceiptsListAdapter
import kotlinx.android.synthetic.main.item_receipt.view.*

class SearchReceiptListAdapter(private val onItemClicked: (receiptId: Long) -> Unit) : BaseReceiptsListAdapter() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_receipt, parent, false))

    override fun getItemCount() = receipts.size

    inner class ViewHolder(view: View) : BaseReceiptsListAdapter.ReceiptViewHolder(view) {

        @SuppressLint("SetTextI18n")
        override fun bind(receipt: Receipt) {
            with(itemView) {
                setOnClickListener { onItemClicked(receipt.id) }
                initImage(receipt.image)
                title.text = receipt.title
                servings.text = context.getString(R.string.servings, receipt.servings)
                readyIn.text = context.getString(R.string.ready_in, receipt.readyInMinutes)
            }
        }

        private fun initImage(imageUrl: String) {
            with(itemView) {
                progress.isVisible = true
                Glide.with(this)
                    .load(imageUrl)
                    .addListener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?,
                            model: Any?,
                            target: Target<Drawable>?,
                            asFirstResource: Boolean
                        ) = true

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            progress.isVisible = false
                            return false
                        }

                    })
                    .centerCrop()
                    .into(image)
            }
        }
    }
}