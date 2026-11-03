package org.example.controller

import org.example.api.WorkoutPlansApi
import org.example.dto.WorkoutPlanRequest
import org.example.dto.WorkoutPlanResponse
import org.example.service.WorkoutPlanService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class WorkoutPlanController(private val service: WorkoutPlanService) : WorkoutPlansApi {

    override fun getWorkoutPlans(): ResponseEntity<List<WorkoutPlanResponse>> {
        return ResponseEntity.ok(service.list())
    }

    override fun createWorkoutPlan(workoutPlanRequest: WorkoutPlanRequest): ResponseEntity<WorkoutPlanResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(workoutPlanRequest))
    }

    override fun getWorkoutPlanById(id: UUID): ResponseEntity<WorkoutPlanResponse> {
        val plan = service.getById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(plan)
    }
}
