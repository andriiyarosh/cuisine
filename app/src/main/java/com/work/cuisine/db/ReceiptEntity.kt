package com.work.cuisine.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Receipt")
data class ReceiptEntity(
    @PrimaryKey val id: Long,
    var image: String,
    val readyInMinutes: Int,
    val servings: Int,
    val title: String,
    val summary: String,
    @Embedded val instruction: InstructionEntity
)