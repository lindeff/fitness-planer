package org.example.controller

import org.example.api.WorkoutPlansApi
import org.springframework.web.bind.annotation.RestController
import org.example.dto.WorkoutPlanRequest
import org.example.dto.WorkoutPlanResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.UUID

@RestController
class WorkoutPlanController : WorkoutPlansApi {

    // In-memory store until a repository/database layer exists
    private val plans = mutableMapOf<UUID, WorkoutPlanResponse>()

    override fun getWorkoutPlans(): ResponseEntity<List<WorkoutPlanResponse>> {
        return ResponseEntity.ok(plans.values.toList())
    }

    override fun createWorkoutPlan(workoutPlanRequest: WorkoutPlanRequest): ResponseEntity<WorkoutPlanResponse> {
        val plan = WorkoutPlanResponse(
            id = UUID.randomUUID(),
            name = workoutPlanRequest.name,
            description = workoutPlanRequest.description,
            exercises = workoutPlanRequest.exercises,
        )
        plans[plan.id] = plan
        return ResponseEntity.status(HttpStatus.CREATED).body(plan)
    }

    override fun getWorkoutPlanById(id: UUID): ResponseEntity<WorkoutPlanResponse> {
        val plan = plans[id] ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(plan)
    }
}
