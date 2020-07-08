package com.work.cuisine.repository

import com.work.cuisine.db.ReceiptDao
import com.work.cuisine.mapper.ReceiptMapper
import com.work.cuisine.network.SpoonacularApi
import com.work.cuisine.network.moodels.instructions.Instruction
import com.work.cuisine.network.moodels.instructions.InstructionStep
import com.work.cuisine.network.moodels.receipt.RandomReceiptsHolder
import com.work.cuisine.network.moodels.receipt.Receipt
import com.work.cuisine.network.moodels.receipt.ReceiptHolder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Named
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class ReceiptsRepositoryImpl @Inject constructor(
    @Named("apiKey") private val apiKey: String,
    private val spoonacularApi: SpoonacularApi,
    private val receiptDao: ReceiptDao,
    private val receiptMapper: ReceiptMapper
) : ReceiptsRepository {

    override suspend fun getReceipt(
        query: String,
        cuisine: String,
        number: Int
    ): ReceiptHolder? = suspendCoroutine { continuation ->
        spoonacularApi.getReceipt(apiKey, query, cuisine, number)
            .enqueue(object : Callback<ReceiptHolder> {
                override fun onFailure(call: Call<ReceiptHolder>, t: Throwable) {
                    t.printStackTrace()
                    continuation.resume(null)
                }

                override fun onResponse(
                    call: Call<ReceiptHolder>,
                    response: Response<ReceiptHolder>
                ) {
                    continuation.resume(response.body())
                }
            })
    }

    override suspend fun getRandomReceipt(): List<Receipt> =
        suspendCoroutine { continuation ->
            spoonacularApi.getRandomReceipt(apiKey)
                .enqueue(object : Callback<RandomReceiptsHolder> {
                    override fun onFailure(call: Call<RandomReceiptsHolder>, t: Throwable) {
                        t.printStackTrace()
                        continuation.resume(emptyList())
                    }

                    override fun onResponse(
                        call: Call<RandomReceiptsHolder>,
                        response: Response<RandomReceiptsHolder>
                    ) {
                        continuation.resume(response.body()?.recipes ?: emptyList())
                    }
                })
        }

    override suspend fun getReceiptInfo(receiptId: Long): Pair<Receipt?, Instruction?> {
        val result: Pair<Receipt?, Instruction?>
        val receipt = receiptDao.findBy(receiptId)
        result = receipt?.let {
            receiptMapper.mapToModel(it)
        } ?: kotlin.run {
            getReceipt(receiptId) to getReceiptInstruction(receiptId)
        }
        return suspendCoroutine { it.resume(result) }
    }

    private suspend fun getReceipt(receiptId: Long): Receipt? = suspendCoroutine { continuation ->
        spoonacularApi.getReceiptInfo(receiptId, apiKey)
            .enqueue(object : Callback<Receipt> {
                override fun onFailure(call: Call<Receipt>, t: Throwable) {
                    t.printStackTrace()
                    continuation.resume(null)
                }

                override fun onResponse(call: Call<Receipt>, response: Response<Receipt>) {
                    continuation.resume(response.body())
                }
            })
    }


    private suspend fun getReceiptInstruction(receiptId: Long): Instruction? =
        suspendCoroutine { continuation ->
            spoonacularApi.getReceiptInstruction(receiptId, apiKey)
                .enqueue(object : Callback<List<Instruction>> {
                    override fun onFailure(call: Call<List<Instruction>>, t: Throwable) {
                        t.printStackTrace()
                        continuation.resume(null)
                    }

                    override fun onResponse(
                        call: Call<List<Instruction>>,
                        response: Response<List<Instruction>>
                    ) {
                        response.body()?.let {
                            if (it.isEmpty())
                                continuation.resume(null)
                            else
                                continuation.resume(it[0])
                        } ?: continuation.resume(null)
                    }
                })
        }

    override suspend fun saveReceipt(receipt: Receipt, instruction: Instruction) {
        receiptDao.insert(receiptMapper.mapToEntity(receipt to instruction))
    }

    override suspend fun deleteReceipt(receiptId: Long) {
        receiptDao.delete(receiptId)
    }

    override suspend fun getSavedReceipts() = receiptMapper.mapToModel(receiptDao.findAll()).map { it.first }
}