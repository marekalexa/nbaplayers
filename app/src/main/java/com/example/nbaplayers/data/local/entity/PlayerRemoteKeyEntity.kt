package com.example.nbaplayers.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.Index

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
