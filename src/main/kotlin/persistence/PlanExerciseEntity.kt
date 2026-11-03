package org.example.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.util.UUID

/**
 * A plan's selection of a catalog exercise (membership row).
 * Own sets/targetRepetitions; null falls back to the exercise catalog defaults.
 */
@Entity
@Table(name = "plan_exercise")
class PlanExerciseEntity(
    @Id
    @Column(nullable = false, updatable = false)
    val id: UUID = UUID.randomUUID(),

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "exercise_id", nullable = false)
    val exercise: ExerciseEntity,

    @Column
    var sets: Int? = null,

    @Column(name = "target_repetitions")
    var targetRepetitions: Int? = null,
)
