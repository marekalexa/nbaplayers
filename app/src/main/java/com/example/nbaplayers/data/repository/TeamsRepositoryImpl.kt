package com.example.nbaplayers.data.repository

import com.example.nbaplayers.data.local.AppDb
import com.example.nbaplayers.data.local.entity.toDomainModel
import com.example.nbaplayers.domain.model.Team
import com.example.nbaplayers.domain.repository.TeamsRepository
import jakarta.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * Implementation of the TeamsRepository interface.
 * Handles data operations for NBA teams, combining local database and remote API data.
 *
 * @property db Local database for team data
 */
class TeamsRepositoryImpl @Inject constructor(
    private val db: AppDb,
) : TeamsRepository {

    /**
     * Retrieves a specific team by its ID.
     * First checks the local database, then falls back to the remote API if needed.
     *
     * @param teamId The ID of the team to retrieve
     * @return Flow of Team object
     */
    override fun teamFlow(id: Int): Flow<Team> = flow {
        val entity = db.teamDao().getTeam(id) ?: error("Team $id not found in DB")
        emit(entity.toDomainModel())
    }.flowOn(Dispatchers.IO)
}