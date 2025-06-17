package com.example.nbaplayers.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.nbaplayers.data.local.entity.TeamEntity
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the teams table.
 * Provides methods to interact with team data in the local database.
 */
@Dao
interface TeamDao {

    /**
     * Retrieves a specific team by its ID.
     *
     * @param teamId The ID of the team to retrieve
     * @return Flow of team data
     */
    @Query("SELECT * FROM teams WHERE id = :teamId")
    fun teamFlow(teamId: Int): Flow<TeamEntity>

    /**
     * Retrieves a single team from the database.
     *
     * @return The team entity if found, or null if not found
     */

    @Query("SELECT * FROM teams WHERE id = :id")
    suspend fun getTeam(id: Int): TeamEntity?

    /**
     * Inserts or updates a list of teams in the database.
     *
     * @param teams List of teams to upsert
     */
    @Upsert
    suspend fun upsertTeams(teams: List<TeamEntity>)

    /**
     * Deletes all teams from the database.
     */
    @Query("DELETE FROM teams")
    suspend fun clear()
}