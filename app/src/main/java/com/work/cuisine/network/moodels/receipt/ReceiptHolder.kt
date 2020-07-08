package com.work.cuisine.network.moodels.receipt

import com.google.gson.annotations.SerializedName
import com.work.cuisine.network.moodels.receipt.Receipt

data class ReceiptHolder(
    val offset: Int,
    val number: Int,
    @SerializedName("results") val receipts: List<Receipt>,
    @SerializedName("totalResults") val receiptNumbers: Int,
    @SerializedName("baseUri") val baseUri: String
)