package com.work.cuisine.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(
    version = 1,
    entities = [ReceiptEntity::class]
)
@TypeConverters(value = [InstructionTypeConverter::class])
abstract class CuisineDatabase : RoomDatabase() {

    abstract fun getReceiptDao(): ReceiptDao

    companion object {
        private val tableName = "Cuisine"
        fun instance(application: Context) =
            Room.databaseBuilder(application, CuisineDatabase::class.java, tableName).build()
    }
}