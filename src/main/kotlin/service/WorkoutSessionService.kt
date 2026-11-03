package org.example.service

import org.example.dto.LoggedExercise
import org.example.dto.RecordedSet
import org.example.dto.WorkoutSessionRequest
import org.example.dto.WorkoutSessionResponse
import org.example.persistence.entity.LoggedExerciseEntity
import org.example.persistence.entity.RecordedSetEntity
import org.example.persistence.entity.WorkoutSessionEntity
import org.example.persistence.WorkoutSessionRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WorkoutSessionService(private val repository: WorkoutSessionRepository) {

    @Transactional
    fun log(request: WorkoutSessionRequest): WorkoutSessionResponse {
        val entity = repository.save(request.toEntity())
        return entity.toDto()
    }
}

fun WorkoutSessionRequest.toEntity(): WorkoutSessionEntity = WorkoutSessionEntity(
    planId = planId,
    date = date,
).apply {
    exercises.addAll(this@toEntity.exercises.map { it.toEntity() })
}

fun LoggedExercise.toEntity(): LoggedExerciseEntity = LoggedExerciseEntity(
    name = name,
    muscleGroup = muscleGroup,
).apply {
    sets.addAll(this@toEntity.sets.orEmpty().map { it.toEntity() })
}

fun RecordedSet.toEntity(): RecordedSetEntity = RecordedSetEntity(
    weightKg = weightKg,
    repetitions = repetitions,
)

fun WorkoutSessionEntity.toDto(): WorkoutSessionResponse = WorkoutSessionResponse(
    id = id,
    planId = planId,
    date = date,
    exercises = exercises.map { it.toDto() },
)

fun LoggedExerciseEntity.toDto(): LoggedExercise = LoggedExercise(
    name = name,
    muscleGroup = muscleGroup,
    sets = sets.map { it.toDto() },
)

fun RecordedSetEntity.toDto(): RecordedSet = RecordedSet(
    weightKg = weightKg,
    repetitions = repetitions,
)
