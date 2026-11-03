package org.example.controller

import org.example.api.WorkoutSessionsApi
import org.example.dto.WorkoutSessionRequest
import org.example.dto.WorkoutSessionResponse
import org.example.service.WorkoutSessionService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class WorkoutSessionController(private val service: WorkoutSessionService) : WorkoutSessionsApi {

    override fun logWorkoutSession(workoutSessionRequest: WorkoutSessionRequest): ResponseEntity<WorkoutSessionResponse> {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.log(workoutSessionRequest))
    }
}
