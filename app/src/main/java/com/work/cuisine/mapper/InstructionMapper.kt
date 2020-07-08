package com.work.cuisine.mapper

import com.work.cuisine.db.InstructionEntity
import com.work.cuisine.db.InstructionStepsWrapper
import com.work.cuisine.network.moodels.instructions.Instruction
import javax.inject.Inject

class InstructionMapper @Inject constructor(private val instructionStepMapper: InstructionStepMapper) :
    Mapper<Instruction, InstructionEntity> {
    override fun mapToModel(entity: InstructionEntity) = with(entity) {
        Instruction(name, instructionStepMapper.mapToModel(steps.instructionSteps))
    }

    override fun mapToModel(entity: List<InstructionEntity>) = entity.map { mapToModel(it) }

    override fun mapToEntity(model: Instruction) = with(model) {
        InstructionEntity(name, InstructionStepsWrapper(instructionStepMapper.mapToEntity(steps)))
    }

    override fun mapToEntity(model: List<Instruction>) = model.map { mapToEntity(it) }
}