package com.example.nbaplayers.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.nbaplayers.domain.model.Player

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
    val heightFeet: Int?,
    val heightInches: Int?,
    val weightPounds: Int?,
    val teamId: Int,
)

fun PlayerEntity.toDomain(team: TeamEntity) = Player(
    id = id,
    firstName = firstName,
    lastName = lastName,
    position = position,
    heightFeet = heightFeet,
    heightInches = heightInches,
    weightPounds = weightPounds,
    team = team.toDomainModel()
)