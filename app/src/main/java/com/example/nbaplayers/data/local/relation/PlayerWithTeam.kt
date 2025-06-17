package com.example.nbaplayers.data.local.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.example.nbaplayers.data.local.entity.PlayerEntity
import com.example.nbaplayers.data.local.entity.TeamEntity
import com.example.nbaplayers.data.local.entity.toDomainModel
import com.example.nbaplayers.domain.model.Player

/**
 * Data class representing a player with their associated team information.
 * Uses Room's @Embedded and @Relation annotations to create a one-to-one relationship
 * between a player and their team.
 *
 * @property player The player entity
 * @property team The associated team entity
 */
data class PlayerWithTeam(
    @Embedded val player: PlayerEntity,
    @Relation(
        parentColumn = "teamId",
        entityColumn = "id"
    )
    val team: TeamEntity
)

/**
 * Converts a PlayerWithTeam to a domain Player model.
 *
 * @return Domain Player model with associated team information
 */
fun PlayerWithTeam.toDomainModel() = Player(
    id = player.id,
    firstName = player.firstName,
    lastName = player.lastName,
    position = player.position,
    height = player.height,
    weight = player.weight,
    team = team.toDomainModel()
)