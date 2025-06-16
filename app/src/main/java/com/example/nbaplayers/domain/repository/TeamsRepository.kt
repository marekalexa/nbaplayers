package com.example.nbaplayers.domain.repository

import com.example.nbaplayers.domain.model.Team
import kotlinx.coroutines.flow.Flow

interface TeamsRepository {
    fun teamFlow(id: Int): Flow<Team>
}