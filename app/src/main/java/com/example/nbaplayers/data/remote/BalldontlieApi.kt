package com.example.nbaplayers.data.remote

import com.example.nbaplayers.data.remote.dto.PlayersResponseDto
import com.example.nbaplayers.data.remote.dto.TeamDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Retrofit interface for the balldontlie API.
 * Provides methods to fetch NBA players data from the remote API.
 */
interface BalldontlieApi {

    /**
     * Retrieves a paginated list of players from the API.
     *
     * @param cursor Pointer to the next page of results, used for pagination
     * @param perPage Number of items per page
     * @return PlayersResponseDto containing the list of players and pagination metadata
     */
    @GET("players")
    suspend fun getPlayers(
        @Query("cursor") cursor: Int?,
        @Query("per_page") perPage: Int,
    ): PlayersResponseDto
}