package com.example.nbaplayers.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.nbaplayers.data.paging.PlayersPagingSource
import com.example.nbaplayers.data.remote.BalldontlieApi
import com.example.nbaplayers.domain.model.Player
import com.example.nbaplayers.domain.repository.PlayersRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow

class PlayersRepositoryImpl @Inject constructor(
    private val api: BalldontlieApi,
) : PlayersRepository {
    override fun playersFlow(): Flow<PagingData<Player>> =
        Pager(PagingConfig(pageSize = 35, initialLoadSize = 35, prefetchDistance = 10)) {
            PlayersPagingSource(api)
        }.flow
}