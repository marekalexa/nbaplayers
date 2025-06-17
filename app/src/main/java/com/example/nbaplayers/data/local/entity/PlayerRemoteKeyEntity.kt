package com.example.nbaplayers.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index

/**
 * Entity class representing pagination metadata for players in the local database.
 * Maps to the 'player_remote_keys' table and stores cursor-based pagination information.
 *
 * @property playerId Unique identifier for the player this key is associated with
 * @property nextCursor Cursor value for the next page of results
 */
@Entity(
    tableName = "player_remote_keys",
    primaryKeys = ["playerId"],
    foreignKeys = [
        ForeignKey(
            entity = PlayerEntity::class,
            parentColumns = ["id"],
            childColumns = ["playerId"],
            onDelete = CASCADE,
            onUpdate = CASCADE
        )
    ],
    indices = [Index("playerId")]
)
data class PlayerRemoteKeyEntity(
    val playerId: Int,
    val nextCursor: Int?
)
