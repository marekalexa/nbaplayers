package com.example.nbaplayers.data.remote.dto

import kotlinx.serialization.Serializable

/**
 * Data Transfer Object representing the paginated response from the players endpoint.
 * Contains a list of players and pagination metadata.
 *
 * @property data List of PlayerDto objects
 * @property meta Pagination metadata
 */
@Serializable
data class PlayersResponseDto(
    val data: List<PlayerDto>,
    val meta: PaginationMetadataDto
)