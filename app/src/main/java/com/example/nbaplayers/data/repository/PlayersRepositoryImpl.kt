package com.example.nbaplayers.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.nbaplayers.data.local.AppDb
import com.example.nbaplayers.data.local.entity.toDomain
import com.example.nbaplayers.data.paging.PlayersRemoteMediator
import com.example.nbaplayers.data.remote.BalldontlieApi
import com.example.nbaplayers.domain.model.Player
import com.example.nbaplayers.domain.repository.PlayersRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val PAGE_SIZE = 35

class PlayersRepositoryImpl @Inject constructor(
    private val db: AppDb,
    private val api: BalldontlieApi,
) : PlayersRepository {

    @OptIn(ExperimentalPagingApi::class)
    override fun playersFlow(): Flow<PagingData<Player>> = Pager(
        config = PagingConfig(
            pageSize = PAGE_SIZE,
            initialLoadSize = PAGE_SIZE,
            prefetchDistance = 0,
            enablePlaceholders = true,
        ),
        remoteMediator = PlayersRemoteMediator(api, db),
        pagingSourceFactory = { db.playerDao().getAllPlayers() }
    ).flow.map { paging ->
        paging.map { wrapper ->
            wrapper.player.toDomain(wrapper.team)
        }
    }

    /** Paging flow with just the players that belong to one team */
    override fun teamPlayersFlow(teamId: Int): Flow<PagingData<Player>> =
        Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false
            ),
            // no RemoteMediator – all data is already cached locally
            pagingSourceFactory = { db.playerDao().playersByTeam(teamId) }
        ).flow.map { paging ->
            paging.map { playerEntity ->
                playerEntity.toDomain(team = null) // team is not needed here
            }
        }

    override fun playerFlow(id: Int): Flow<Player> =
        db.playerDao()
            .getPlayer(id)
            .map { it.player.toDomain(it.team) }
}