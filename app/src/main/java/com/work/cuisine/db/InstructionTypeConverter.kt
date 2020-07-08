package com.work.cuisine.db

import androidx.room.TypeConverter
import com.google.gson.Gson

class InstructionTypeConverter {

    @TypeConverter
    fun toWrapper(instructionStep: String) = Gson().fromJson(instructionStep, InstructionStepsWrapper::class.java)

    @TypeConverter
    fun toString(instructionStepsWrapper: InstructionStepsWrapper) = Gson().toJson(instructionStepsWrapper)
}