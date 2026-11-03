package org.example.controller

import org.example.api.ExercisesApi
import org.example.service.ExerciseService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

/** GET /exercises — the predefined catalog users select from. */
@RestController
class ExerciseController(private val service: ExerciseService) : ExercisesApi {

    override fun getExercises(): ResponseEntity<List<org.example.dto.Exercise>> {
        return ResponseEntity.ok(service.list())
    }

    override fun getExerciseById(id: UUID): ResponseEntity<org.example.dto.Exercise> {
        val exercise = service.getById(id) ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(exercise)
    }
}
