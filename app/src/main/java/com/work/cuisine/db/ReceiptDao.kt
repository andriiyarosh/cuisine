package com.work.cuisine.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReceiptDao {

    @Insert
    fun insert(receiptEntity: ReceiptEntity)

    @Query("DELETE FROM RECEIPT WHERE id=:receiptId")
    fun delete(receiptId: Long)

    @Query("SELECT * FROM RECEIPT WHERE id=:receiptId")
    fun findBy(receiptId: Long): ReceiptEntity?

    @Query("SELECT * FROM RECEIPT")
    fun findAll(): List<ReceiptEntity>

    @Query("SELECT COUNT(*) FROM RECEIPT WHERE id=:receiptId")
    fun isSaved(receiptId: Long): Int
}