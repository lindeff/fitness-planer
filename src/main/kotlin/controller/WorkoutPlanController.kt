package org.example.controller

import org.example.api.WorkoutPlansApi
import org.example.dto.LastSessionResponse
import org.example.dto.WorkoutPlanRequest
import org.example.dto.WorkoutPlanResponse
import org.example.service.WorkoutPlanService
import org.example.service.WorkoutSessionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class WorkoutPlanController(
    private val service: WorkoutPlanService,
    private val sessionService: WorkoutSessionService,
) : WorkoutPlansApi {

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

    /** Statistics of the plan's last session, to prefill a new one. */
    override fun getPlanLastSession(id: UUID): ResponseEntity<LastSessionResponse> {
        val lastSession = sessionService.lastSessionForPlan(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(lastSession)
    }
}
