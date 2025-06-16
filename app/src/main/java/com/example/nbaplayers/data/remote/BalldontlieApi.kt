package com.example.nbaplayers.data.remote

import com.example.nbaplayers.data.remote.dto.PlayersResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface BalldontlieApi {
    @GET("players")
    suspend fun getPlayers(
        @Query("cursor") cursor: Int? = null,
        @Query("per_page") perPage: Int = 35,
    ): PlayersResponseDto
}