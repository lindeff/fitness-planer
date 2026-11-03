package org.example.service

import org.example.dto.Exercise
import org.example.dto.ExerciseSelection
import org.example.dto.PlanExercise
import org.example.dto.WorkoutPlanRequest
import org.example.dto.WorkoutPlanResponse
import org.example.persistence.entity.ExerciseEntity
import org.example.persistence.entity.PlanExerciseEntity
import org.example.persistence.entity.WorkoutPlanEntity
import org.example.persistence.ExerciseRepository
import org.example.persistence.WorkoutPlanRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@Service
class WorkoutPlanService(
    private val planRepository: WorkoutPlanRepository,
    private val exerciseRepository: ExerciseRepository,
) {

    @Transactional(readOnly = true)
    fun list(): List<WorkoutPlanResponse> =
        planRepository.findAllByOrderByNameAsc().map { it.toDto() }

    @Transactional(readOnly = true)
    fun getById(id: UUID): WorkoutPlanResponse? =
        planRepository.findById(id).map { it.toDto() }.orElse(null)

    @Transactional
    fun create(request: WorkoutPlanRequest): WorkoutPlanResponse {
        val entity = WorkoutPlanEntity(
            name = request.name,
            description = request.description,
        )
        entity.exercises.addAll(
            request.exercises.map { selection ->
                selection.toEntity(exerciseRepository)
            }
        )
        return planRepository.save(entity).toDto()
    }
}

/**
 * Resolves the selected catalog exercise.
 * Omitted sets / targetRepetitions fall back to the catalog defaults.
 */
fun ExerciseSelection.toEntity(repository: ExerciseRepository): PlanExerciseEntity {
    val catalog = repository.findById(exerciseId)
        .orElseThrow { ResponseStatusException(HttpStatus.BAD_REQUEST, "Unknown exercise id: $exerciseId") }
    return PlanExerciseEntity(
        exercise = catalog,
        sets = sets ?: catalog.sets,
        targetRepetitions = targetRepetitions ?: catalog.targetRepetitions,
    )
}

fun WorkoutPlanEntity.toDto(): WorkoutPlanResponse = WorkoutPlanResponse(
    id = id,
    name = name,
    description = description,
    exercises = exercises.map { it.toDto() },
)

fun PlanExerciseEntity.toDto(): PlanExercise = PlanExercise(
    exerciseId = exercise.id,
    name = exercise.name,
    muscleGroup = exercise.muscleGroup,
    sets = sets,
    targetRepetitions = targetRepetitions,
)

fun ExerciseEntity.toDto(): Exercise = Exercise(
    id = id,
    name = name,
    muscleGroup = muscleGroup,
    sets = sets,
    targetRepetitions = targetRepetitions,
)
