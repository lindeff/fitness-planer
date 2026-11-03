package org.example.persistence.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "workout_plan")
class WorkoutPlanEntity(
    @Id
    @Column(nullable = false, updatable = false)
    val id: UUID = UUID.randomUUID(),

    @Column(nullable = false)
    var name: String,

    @Column
    var description: String? = null,

    /**
     * Unidirectional one-to-many with a shared join column;
     * cascade + orphanRemoval so exercises live and die with their plan.
     */
    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "plan_id")
    val exercises: MutableList<ExerciseEntity> = mutableListOf(),
)
