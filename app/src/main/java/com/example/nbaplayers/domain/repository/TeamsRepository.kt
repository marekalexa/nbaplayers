package com.example.nbaplayers.domain.repository

import com.example.nbaplayers.domain.model.Team
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for accessing team data.
 */
interface TeamsRepository {

    /**
     * Returns a flow of a single team by ID.
     *
     * @param id ID of the team.
     */
    fun teamFlow(id: Int): Flow<Team>
}
