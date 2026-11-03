package org.example.service

import org.example.persistence.ExerciseRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

@Service
class ExerciseService(private val repository: ExerciseRepository) {

    @Transactional(readOnly = true)
    fun list() = repository.findAllByOrderByNameAsc().map { it.toDto() }

    @Transactional(readOnly = true)
    fun getById(id: UUID) =
        repository.findById(id).map { it.toDto() }.orElse(null)
}
