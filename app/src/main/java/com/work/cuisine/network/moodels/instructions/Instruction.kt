package com.work.cuisine.network.moodels.instructions

import com.google.gson.annotations.SerializedName

data class Instruction(
    @SerializedName("name")val name: String = "",
    val steps: List<InstructionStep>
)