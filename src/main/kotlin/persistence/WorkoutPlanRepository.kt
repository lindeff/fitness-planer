package org.example.persistence

import org.example.persistence.entity.WorkoutPlanEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface WorkoutPlanRepository : JpaRepository<WorkoutPlanEntity, UUID>
