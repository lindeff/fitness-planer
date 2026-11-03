package org.example.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.example.dto.MuscleGroup
import java.util.UUID

/** Standalone, predefined exercise (the catalog users select from). */
@Entity
@Table(name = "exercise")
class ExerciseEntity(
    @Id
    @Column(nullable = false, updatable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    var name: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "muscle_group")
    var muscleGroup: MuscleGroup? = null,

    @Column
    var sets: Int? = null,

    @Column(name = "target_repetitions")
    var targetRepetitions: Int? = null,
)
