package com.example.nbaplayers.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * One-to-one relation wrapper returned by Room.
 * - `@Embedded` copies all columns of PlayerEntity.
 * - `@Relation` fetches the matching TeamEntity.
 */
data class PlayerWithTeamEntity(
    @Embedded
    val player: PlayerEntity,

    @Relation(
        parentColumn = "teamId",
        entityColumn = "id"
    )
    val team: TeamEntity
)