package com.example.nbaplayers.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class PlayersResponseDto(
    val data: List<PlayerDto>,
    val meta: ResponseMetadataDto
)