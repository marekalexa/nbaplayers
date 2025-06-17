package com.example.nbaplayers.data.local.entity

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Entity class representing a player with their associated team information.
 * Uses Room's @Embedded and @Relation annotations to create a one-to-one relationship
 * between a player and their team.
 *
 * @property player The player entity
 * @property team The associated team entity
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