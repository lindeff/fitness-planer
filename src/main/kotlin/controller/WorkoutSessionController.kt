package org.example.controller

import org.example.api.WorkoutSessionsApi
import org.springframework.web.bind.annotation.RestController
import org.example.dto.WorkoutSessionRequest
import org.example.dto.WorkoutSessionResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import java.util.UUID

// TODO: persist sessions; 404 could be returned if the referenced plan does not exist
@RestController
class WorkoutSessionController : WorkoutSessionsApi {

    override fun logWorkoutSession(workoutSessionRequest: WorkoutSessionRequest): ResponseEntity<WorkoutSessionResponse> {
        val session = WorkoutSessionResponse(
            id = UUID.randomUUID(),
            planId = workoutSessionRequest.planId,
            date = workoutSessionRequest.date,
            exercises = workoutSessionRequest.exercises,
        )
        return ResponseEntity.status(HttpStatus.CREATED).body(session)
    }
}
