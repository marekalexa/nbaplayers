package com.example.nbaplayers.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.nbaplayers.data.remote.BalldontlieApi
import com.example.nbaplayers.data.remote.dto.toDomainModel
import com.example.nbaplayers.domain.model.Player

class PlayersPagingSource(
    private val api: BalldontlieApi
) : PagingSource<Int, Player>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Player> {
        val cursor = params.key
        return try {
            val resp = api.getPlayers(cursor = cursor, perPage = params.loadSize)
            val mappedPlayers = resp.data.map { it.toDomainModel() }

            LoadResult.Page(
                data = mappedPlayers,
                prevKey = null,
                nextKey = resp.meta.nextCursor
            )
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Player>): Int? =
        null
}
