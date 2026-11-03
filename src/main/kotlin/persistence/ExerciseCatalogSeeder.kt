package org.example.persistence

import com.fasterxml.jackson.databind.ObjectMapper
import org.example.persistence.entity.ExerciseEntity
import org.example.dto.MuscleGroup
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.UUID

/**
 * Seeds the exercise catalog from classpath:seed/exercises.json on first startup
 * (only when the table is empty, so a deliberate wipe re-seeds automatically).
 */
@Component
class ExerciseCatalogSeeder(
    private val repository: ExerciseRepository,
) : ApplicationRunner {

    @Transactional
    override fun run(applicationArguments: ApplicationArguments) {
        if (repository.count() > 0L) return

        val stream = javaClass.getResourceAsStream("/seed/exercises.json") ?: return
        val root = ObjectMapper().readTree(stream)
        val entries = root.get("exercises")

        for (i in 0 until entries.size()) {
            val node = entries.get(i)
            repository.save(
                ExerciseEntity(
                    id = UUID.fromString(node.get("id").asText()),
                    name = node.get("name").asText(),
                    muscleGroup = if (node.hasNonNull("muscleGroup")) MuscleGroup.valueOf(node.get("muscleGroup").asText()) else null,
                    sets = if (node.hasNonNull("defaultSets")) node.get("defaultSets").asInt() else null,
                    targetRepetitions = if (node.hasNonNull("defaultTargetRepetitions")) node.get("defaultTargetRepetitions").asInt() else null,
                )
            )
        }
    }
}
