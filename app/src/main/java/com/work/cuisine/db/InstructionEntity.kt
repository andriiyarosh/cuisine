package com.work.cuisine.db

data class InstructionEntity(
    val name: String,
    val steps: InstructionStepsWrapper
)