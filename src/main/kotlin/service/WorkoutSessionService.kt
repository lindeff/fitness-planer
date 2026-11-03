package org.example.service

import org.example.dto.LastSessionExercise
import org.example.dto.LastSessionResponse
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
import java.util.UUID

@Service
class WorkoutSessionService(private val repository: WorkoutSessionRepository) {

    @Transactional
    fun log(request: WorkoutSessionRequest): WorkoutSessionResponse {
        val entity = repository.save(request.toEntity())
        return entity.toDto()
    }

    /**
     * Statistics of the plan's most recent session, used to prefill a new session.
     * Returns null when nothing has been logged for this plan yet (controller -> 404).
     */
    @Transactional(readOnly = true)
    fun lastSessionForPlan(planId: UUID): LastSessionResponse? =
        repository.findFirstByPlanIdOrderByDateDesc(planId)?.toLastSessionDto()
}

fun WorkoutSessionEntity.toLastSessionDto(): LastSessionResponse = LastSessionResponse(
    sessionId = id,
    planId = planId,
    date = date,
    exercises = exercises.map { it.toLastSessionDto() },
)

fun LoggedExerciseEntity.toLastSessionDto(): LastSessionExercise {
    val recordedWeights = sets.mapNotNull { it.weightKg }
    return LastSessionExercise(
        exerciseId = exerciseId,
        name = name,
        muscleGroup = muscleGroup,
        sets = sets.map { it.toDto() },
        maxWeightKg = recordedWeights.maxOrNull(),
    )
}

fun WorkoutSessionRequest.toEntity(): WorkoutSessionEntity = WorkoutSessionEntity(
    planId = planId,
    date = date,
).apply {
    exercises.addAll(this@toEntity.exercises.map { it.toEntity() })
}

fun LoggedExercise.toEntity(): LoggedExerciseEntity = LoggedExerciseEntity(
    exerciseId = exerciseId,
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
    exerciseId = exerciseId,
    name = name,
    muscleGroup = muscleGroup,
    sets = sets.map { it.toDto() },
)

fun RecordedSetEntity.toDto(): RecordedSet = RecordedSet(
    weightKg = weightKg,
    repetitions = repetitions,
)
