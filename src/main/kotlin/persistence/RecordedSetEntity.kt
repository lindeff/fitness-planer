package org.example.persistence.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.UUID

@Entity
@Table(name = "recorded_set")
class RecordedSetEntity(
    @Id
    @Column(nullable = false, updatable = false)
    val id: UUID = UUID.randomUUID(),

    @Column
    var weightKg: Double? = null,

    @Column(nullable = false)
    var repetitions: Int,
)
