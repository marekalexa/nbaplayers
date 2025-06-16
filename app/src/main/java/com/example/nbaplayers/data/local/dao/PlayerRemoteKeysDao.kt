package com.example.nbaplayers.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.nbaplayers.data.local.entity.PlayerRemoteKeyEntity

@Dao
interface PlayerRemoteKeysDao {

    /** Upsert the batch of keys for the last response */
    @Upsert
    suspend fun upsertAll(keys: List<PlayerRemoteKeyEntity>)

    /** Remote key that belongs to one concrete Player row */
    @Query("SELECT * FROM player_remote_keys WHERE playerId = :id")
    suspend fun remoteKeyByPlayerId(id: Int): PlayerRemoteKeyEntity?

    /** The highest (latest) cursor we have on disk */
    @Query("SELECT nextCursor FROM player_remote_keys WHERE nextCursor IS NOT NULL ORDER BY nextCursor DESC LIMIT 1")
    suspend fun lastCursor(): Int?

    /** Nukes the table (used on REFRESH) */
    @Query("DELETE FROM player_remote_keys")
    suspend fun clear()
}
