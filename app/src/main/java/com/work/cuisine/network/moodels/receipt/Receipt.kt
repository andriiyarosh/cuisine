package com.work.cuisine.network.moodels.receipt

import com.google.gson.annotations.SerializedName

data class Receipt(
    @SerializedName("id") val id: Long,
    @SerializedName("image") var image: String,
    @SerializedName("readyInMinutes") val readyInMinutes: Int,
    @SerializedName("servings") val servings: Int,
    @SerializedName("title") val title: String,
    @SerializedName("summary") val summary: String,
    val isSaved: Boolean = false
)