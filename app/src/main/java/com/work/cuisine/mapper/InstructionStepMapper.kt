package com.work.cuisine.mapper

import com.work.cuisine.db.InstructionStepEntity
import com.work.cuisine.network.moodels.instructions.InstructionStep
import javax.inject.Inject

class InstructionStepMapper @Inject constructor(): Mapper<InstructionStep, InstructionStepEntity> {

    override fun mapToModel(entity: InstructionStepEntity) = with(entity) {
        InstructionStep(number, step)
    }

    override fun mapToModel(entity: List<InstructionStepEntity>) = entity.map { mapToModel(it) }

    override fun mapToEntity(model: InstructionStep) = with(model) {
        InstructionStepEntity(number, step)
    }

    override fun mapToEntity(model: List<InstructionStep>) = model.map { mapToEntity(it) }
}