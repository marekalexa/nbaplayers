package com.example.nbaplayers.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.nbaplayers.BuildConfig
import com.example.nbaplayers.data.local.AppDb
import com.example.nbaplayers.data.local.entity.PlayerRemoteKeyEntity
import com.example.nbaplayers.data.local.relation.PlayerWithTeam
import com.example.nbaplayers.data.remote.BalldontlieApi
import com.example.nbaplayers.data.remote.dto.toLocal
import kotlinx.coroutines.delay

/**
 * RemoteMediator implementation for handling pagination of player data.
 * Manages the loading of player data from the remote API and caching in the local database.
 *
 * @property api Remote API for fetching player data
 * @property db Local database for caching player data
 */
@OptIn(ExperimentalPagingApi::class)
class PlayersRemoteMediator(
    private val api: BalldontlieApi,
    private val db: AppDb
) : RemoteMediator<Int, PlayerWithTeam>() {

    override suspend fun initialize(): InitializeAction = InitializeAction.LAUNCH_INITIAL_REFRESH

    /**
     * Loads data from the remote source and updates the local database.
     * Handles initial load, refresh, and append operations.
     *
     * @param loadType The type of load operation (REFRESH, PREPEND, or APPEND)
     * @param state The current state of the paging data
     * @return MediatorResult indicating the success or failure of the load operation
     */
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, PlayerWithTeam>
    ): MediatorResult {
        return try {
            val cursor = when (loadType) {
                LoadType.REFRESH -> null
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)

                LoadType.APPEND -> {
                    // Prefer the key that belongs to the list snapshot
                    val remoteKey = getRemoteKeyForLastItem(state)
                    remoteKey?.nextCursor
                        ?: db.playerRemoteKeysDao().lastCursor()
                        ?: return MediatorResult.Success(endOfPaginationReached = true)
                }
            }


            if (loadType == LoadType.APPEND && cursor == null) {
                return MediatorResult.Success(endOfPaginationReached = true)
            }

            val resp = api.getPlayers(cursor, state.config.pageSize)
            if (BuildConfig.DEBUG) {
                delay(3000) // Simulate network delay to see the loading state in UI
            }
            val players = resp.data.map { it.toLocal() }
            val teams = resp.data.map { it.team.toLocal() }
            val keys = players.map { p ->
                PlayerRemoteKeyEntity(playerId = p.id, nextCursor = resp.meta.nextCursor)
            }

            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.playerDao().clear()
                    db.teamDao().clear()
                    db.playerRemoteKeysDao().clear()
                }

                db.teamDao().upsertTeams(teams)
                db.playerDao().upsertPlayers(players)
                db.playerRemoteKeysDao().upsertAll(keys)
            }

            MediatorResult.Success(endOfPaginationReached = resp.meta.nextCursor == null)

        } catch (t: Throwable) {
            MediatorResult.Error(t)
        }
    }

    /**
     * Retrieves the remote key for the last item in the current state.
     *
     * @param state The current state of the paging data
     * @return The remote key for the last item, or null if not found
     */
    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, PlayerWithTeam>
    ): PlayerRemoteKeyEntity? {
        // Find the last page that has data
        val lastPage = state.pages.lastOrNull { it.data.isNotEmpty() }
        // The very last item in that page
        val lastItem = lastPage?.data?.lastOrNull()
        return lastItem?.let { db.playerRemoteKeysDao().getRemoteKeyForPlayer(it.player.id) }
    }
}
