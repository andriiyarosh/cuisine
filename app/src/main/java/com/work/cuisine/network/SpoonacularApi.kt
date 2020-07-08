package com.work.cuisine.network

import com.work.cuisine.network.moodels.instructions.Instruction
import com.work.cuisine.network.moodels.receipt.RandomReceiptsHolder
import com.work.cuisine.network.moodels.receipt.Receipt
import com.work.cuisine.network.moodels.receipt.ReceiptHolder
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SpoonacularApi {

    @GET("recipes/search")
    fun getReceipt(
        @Query("apiKey") apiKey: String,
        @Query("query") query: String,
        @Query("cuisine") cuisine: String,
        @Query("number") number: Int
    ): Call<ReceiptHolder>

    @GET("recipes/random")
    fun getRandomReceipt(
        @Query("apiKey") apiKey: String
    ): Call<RandomReceiptsHolder>

    @GET("recipes/{id}/information")
    fun getReceiptInfo(
        @Path("id") receiptId: Long,
        @Query("apiKey") apiKey: String
    ): Call<Receipt>

    @GET("recipes/{id}/analyzedInstructions")
    fun getReceiptInstruction(
        @Path("id") receiptId: Long,
        @Query("apiKey") apiKey: String
    ): Call<List<Instruction>>
}
