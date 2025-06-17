package com.example.nbaplayers.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.nbaplayers.data.local.entity.PlayerEntity
import com.example.nbaplayers.data.local.relation.PlayerWithTeam
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the players table.
 * Provides methods to interact with player data in the local database.
 */
@Dao
interface PlayerDao {

    /**
     * Retrieves all players with their associated teams, ordered by player ID.
     * Used for paging through the complete list of players.
     *
     * @return PagingSource for paginated player data
     */
    @Transaction
    @Query("SELECT * FROM players ORDER BY id")
    fun getAllPlayers(): PagingSource<Int, PlayerWithTeam>

    /**
     * Retrieves a specific player with their team information.
     *
     * @param playerId The ID of the player to retrieve
     * @return Flow of PlayerWithTeam data
     */
    @Transaction
    @Query("SELECT * FROM players WHERE id = :playerId")
    fun getPlayer(playerId: Int): Flow<PlayerWithTeam>

    /**
     * Retrieves all players that belong to a specific team, ordered by player ID.
     * Used for displaying team roster.
     *
     * @param teamId The ID of the team
     * @return PagingSource for paginated player data
     */
    @Query(
        """
        SELECT * FROM players
        WHERE teamId = :teamId
        ORDER BY id
        """
    )
    fun playersByTeam(teamId: Int): PagingSource<Int, PlayerEntity>

    /**
     * Inserts or updates a list of players in the database.
     *
     * @param players List of players to upsert
     */
    @Upsert
    suspend fun upsertPlayers(players: List<PlayerEntity>)

    /**
     * Retrieves all player IDs from the database.
     *
     * @return List of player IDs
     */
    @Query("SELECT id FROM players")
    suspend fun ids(): List<Int>

    /**
     * Deletes all players from the database.
     */
    @Query("DELETE FROM players")
    suspend fun clear()
}