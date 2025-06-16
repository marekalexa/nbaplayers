package com.example.nbaplayers.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.nbaplayers.data.local.entity.PlayerEntity
import com.example.nbaplayers.data.local.entity.TeamEntity

/**
 * One-to-one relation wrapper returned by Room queries
 * so you get both the raw player row **and** its team row.
 */
data class PlayerWithTeam(
    @Embedded val player: PlayerEntity,
    @Relation(
        parentColumn = "teamId",
        entityColumn = "id"
    )
    val team: TeamEntity
)