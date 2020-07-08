package com.work.cuisine.mapper

import com.work.cuisine.db.ReceiptEntity
import com.work.cuisine.network.moodels.instructions.Instruction
import com.work.cuisine.network.moodels.receipt.Receipt
import javax.inject.Inject

class ReceiptMapper @Inject constructor(private val instructionMapper: InstructionMapper) :
    Mapper<Pair<Receipt, Instruction>, ReceiptEntity> {

    override fun mapToModel(entity: ReceiptEntity) = with(entity) {
        Receipt(
            id,
            image,
            readyInMinutes,
            servings,
            title,
            summary,
            true
        ) to instructionMapper.mapToModel(instruction)
    }

    override fun mapToModel(entity: List<ReceiptEntity>) = entity.map { mapToModel(it) }

    override fun mapToEntity(model: Pair<Receipt, Instruction>) = with(model) {
        ReceiptEntity(
            first.id,
            first.image,
            first.readyInMinutes,
            first.servings,
            first.title,
            first.summary,
            instructionMapper.mapToEntity(second)
        )
    }

    override fun mapToEntity(model: List<Pair<Receipt, Instruction>>) =
        model.map { mapToEntity(it) }
}