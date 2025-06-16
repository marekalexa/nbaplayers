package com.example.nbaplayers.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.nbaplayers.data.local.entity.TeamEntity

@Dao
interface TeamDao {
    @Upsert
    suspend fun upsertTeams(teams: List<TeamEntity>)

    @Query("SELECT * FROM teams WHERE id = :id")
    suspend fun getTeam(id: Int): TeamEntity?

    /** Delete all teams rows */
    @Query("DELETE FROM teams")
    suspend fun clear()
}