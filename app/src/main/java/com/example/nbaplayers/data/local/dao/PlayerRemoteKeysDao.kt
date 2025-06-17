package com.example.nbaplayers.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.nbaplayers.data.local.entity.PlayerRemoteKeyEntity

/**
 * Data Access Object for the player remote keys table.
 * Manages pagination metadata for the players list.
 */
@Dao
interface PlayerRemoteKeysDao {

    /**
     * Retrieves the remote key for a specific player.
     *
     * @param playerId The ID of the player
     * @return The remote key entity for the player, or null if not found
     */
    @Query("SELECT * FROM player_remote_keys WHERE playerId = :playerId")
    suspend fun getRemoteKeyForPlayer(playerId: Int): PlayerRemoteKeyEntity?

    /**
     * Retrieves the last cursor from the remote keys table.
     * Used for pagination to determine the next page to load.
     *
     * @return The next cursor value, or null if no more pages
     */
    @Query("SELECT nextCursor FROM player_remote_keys ORDER BY playerId DESC LIMIT 1")
    suspend fun lastCursor(): Int?

    /**
     * Inserts or updates a list of remote keys.
     *
     * @param keys List of remote keys to upsert
     */
    @Upsert
    suspend fun upsertAll(keys: List<PlayerRemoteKeyEntity>)

    /**
     * Deletes all remote keys from the database.
     */
    @Query("DELETE FROM player_remote_keys")
    suspend fun clear()
}
