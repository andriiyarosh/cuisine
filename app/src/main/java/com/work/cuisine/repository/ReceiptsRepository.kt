package com.work.cuisine.repository

import com.work.cuisine.network.moodels.instructions.Instruction
import com.work.cuisine.network.moodels.receipt.Receipt
import com.work.cuisine.network.moodels.receipt.ReceiptHolder

interface ReceiptsRepository {

    suspend fun getReceipt(
        query: String,
        cuisine: String = "",
        number: Int = 1
    ): ReceiptHolder?

    suspend fun getRandomReceipt(): List<Receipt>

    suspend fun getReceiptInfo(receiptId: Long): Pair<Receipt?, Instruction?>

    suspend fun saveReceipt(receipt: Receipt, instruction: Instruction)

    suspend fun deleteReceipt(receiptId: Long)

    suspend fun getSavedReceipts(): List<Receipt>
}