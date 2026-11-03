package org.example.service

import org.example.dto.Exercise
import org.example.dto.WorkoutPlanRequest
import org.example.dto.WorkoutPlanResponse
import org.example.persistence.entity.ExerciseEntity
import org.example.persistence.entity.WorkoutPlanEntity
import org.example.persistence.WorkoutPlanRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class WorkoutPlanService(private val repository: WorkoutPlanRepository) {

    @Transactional(readOnly = true)
    fun list(): List<WorkoutPlanResponse> =
        repository.findAll().map { it.toDto() }

    @Transactional(readOnly = true)
    fun getById(id: UUID): WorkoutPlanResponse? =
        repository.findById(id).map { it.toDto() }.orElse(null)

    @Transactional
    fun create(request: WorkoutPlanRequest): WorkoutPlanResponse {
        val entity = repository.save(request.toEntity())
        return entity.toDto()
    }
}

fun WorkoutPlanRequest.toEntity(): WorkoutPlanEntity = WorkoutPlanEntity(
    name = name,
    description = description,
).apply {
    exercises.addAll(this@toEntity.exercises.map { it.toEntity() })
}

fun Exercise.toEntity(): ExerciseEntity = ExerciseEntity(
    name = name,
    muscleGroup = muscleGroup,
    sets = sets,
    targetRepetitions = targetRepetitions,
)

fun WorkoutPlanEntity.toDto(): WorkoutPlanResponse = WorkoutPlanResponse(
    id = id,
    name = name,
    description = description,
    exercises = exercises.map { it.toDto() },
)

fun ExerciseEntity.toDto(): Exercise = Exercise(
    name = name,
    muscleGroup = muscleGroup,
    sets = sets,
    targetRepetitions = targetRepetitions,
)
