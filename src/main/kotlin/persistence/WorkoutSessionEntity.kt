package org.example.persistence.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.Table
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "workout_session")
class WorkoutSessionEntity(
    @Id
    @Column(nullable = false, updatable = false)
    val id: UUID = UUID.randomUUID(),

    /** Reference to the plan this session was based on (kept as plain UUID to allow independent deletion). */
    @Column(name = "plan_id", nullable = false)
    val planId: UUID,

    @Column(nullable = false)
    var date: LocalDate,

    @OneToMany(cascade = [CascadeType.ALL], orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "session_id")
    val exercises: MutableList<LoggedExerciseEntity> = mutableListOf(),
)
