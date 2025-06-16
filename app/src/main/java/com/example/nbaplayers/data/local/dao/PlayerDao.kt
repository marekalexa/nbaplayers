package com.example.nbaplayers.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.nbaplayers.data.local.entity.PlayerEntity
import com.example.nbaplayers.data.local.relation.PlayerWithTeam
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayerDao {

    @Transaction
    @Query("SELECT * FROM players ORDER BY id")
    fun getAllPlayers(): PagingSource<Int, PlayerWithTeam>

    @Query("SELECT * FROM players WHERE id = :playerId")
    fun getPlayer(playerId: Int): Flow<PlayerWithTeam>

    @Upsert
    suspend fun upsertPlayers(players: List<PlayerEntity>)

    @Query("DELETE FROM players")
    suspend fun clear()
}