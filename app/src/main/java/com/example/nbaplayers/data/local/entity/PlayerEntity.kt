package com.example.nbaplayers.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.nbaplayers.domain.model.Player

/**
 * Entity class representing a player in the local database.
 * Maps to the 'players' table and includes a foreign key relationship to teams.
 *
 * @property id Unique identifier for the player
 * @property firstName Player's first name
 * @property lastName Player's last name
 * @property position Player's position on the court
 * @property height Player's height (optional)
 * @property weight Player's weight (optional)
 * @property teamId Foreign key reference to the player's team
 */
@Entity(
    tableName = "players",
    foreignKeys = [
        ForeignKey(
            entity = TeamEntity::class,
            parentColumns = ["id"],
            childColumns = ["teamId"],
            onDelete = CASCADE
        )
    ],
    indices = [Index("teamId")]
)
data class PlayerEntity(
    @PrimaryKey val id: Int,
    val firstName: String,
    val lastName: String,
    val position: String,
    val height: String?,
    val weight: String?,
    val teamId: Int,
)

/**
 * Converts a PlayerEntity to a domain Player model.
 *
 * @param team The associated team entity, if any
 * @return Domain Player model
 */
fun PlayerEntity.toDomain(team: TeamEntity?) = Player(
    id = id,
    firstName = firstName,
    lastName = lastName,
    position = position,
    height = height,
    weight = weight,
    team = team?.toDomainModel()
)