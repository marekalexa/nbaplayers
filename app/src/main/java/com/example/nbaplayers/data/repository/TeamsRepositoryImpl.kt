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

class TeamsRepositoryImpl @Inject constructor(
    private val db: AppDb,
) : TeamsRepository {

    override fun teamFlow(id: Int): Flow<Team> = flow {
        val entity = db.teamDao().getTeam(id) ?: error("Team $id not found in DB")
        emit(entity.toDomainModel())
    }.flowOn(Dispatchers.IO)
}