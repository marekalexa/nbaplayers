package com.example.nbaplayers.domain.repository

import androidx.paging.PagingData
import com.example.nbaplayers.domain.model.Player
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for accessing player data.
 */
interface PlayersRepository {

    /**
     * Returns a paginated flow of all NBA players.
     */
    fun playersFlow(): Flow<PagingData<Player>>

    /**
     * Returns a paginated flow of players for a specific team.
     *
     * @param teamId ID of the team.
     */
    fun teamPlayersFlow(teamId: Int): Flow<PagingData<Player>>

    /**
     * Returns a flow of a single player by ID.
     *
     * @param id ID of the player.
     */
    fun playerFlow(id: Int): Flow<Player>
}
