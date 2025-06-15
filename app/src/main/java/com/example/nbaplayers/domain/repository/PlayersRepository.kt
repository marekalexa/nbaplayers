package com.example.nbaplayers.domain.repository

import androidx.paging.PagingData
import com.example.nbaplayers.domain.model.Player
import kotlinx.coroutines.flow.Flow

interface PlayersRepository {
    fun playersFlow(): Flow<PagingData<Player>>
}