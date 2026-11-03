package org.example.persistence

import org.example.persistence.entity.ExerciseEntity
import org.example.persistence.entity.WorkoutPlanEntity
import org.example.persistence.entity.WorkoutSessionEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface ExerciseRepository : JpaRepository<ExerciseEntity, UUID> {
    fun findAllByOrderByNameAsc(): List<ExerciseEntity>
}

interface WorkoutPlanRepository : JpaRepository<WorkoutPlanEntity, UUID> {
    fun findAllByOrderByNameAsc(): List<WorkoutPlanEntity>
}

interface WorkoutSessionRepository : JpaRepository<WorkoutSessionEntity, UUID> {
    /** Most recent session for a plan (date first, then id as tie-breaker). */
    fun findFirstByPlanIdOrderByDateDesc(planId: UUID): WorkoutSessionEntity?
}
