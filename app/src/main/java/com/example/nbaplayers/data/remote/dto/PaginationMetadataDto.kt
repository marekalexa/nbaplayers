package com.example.nbaplayers.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object representing pagination metadata from the balldontlie API.
 * Contains information about the current page and total number of items.
 *
 * @property nextCursor The cursor for the next page of results, or null if there are no more pages
 * @property perPage The number of items per page
 */
@Serializable
data class PaginationMetadataDto(
    @SerialName("next_cursor") val nextCursor: Int? = null,
    @SerialName("per_page") val perPage: Int,
)
