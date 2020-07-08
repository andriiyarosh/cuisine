package com.work.cuisine.network.moodels.instructions

import com.google.gson.annotations.SerializedName

data class InstructionStep(
    @SerializedName("number") val number: Int,
    @SerializedName("step") val step: String
)