package org.example.persistence

import org.example.persistence.entity.WorkoutSessionEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface WorkoutSessionRepository : JpaRepository<WorkoutSessionEntity, UUID>
