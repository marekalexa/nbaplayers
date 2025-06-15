package com.example.nbaplayers.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ResponseMetadataDto(
    @SerialName("next_cursor") val nextCursor: Int? = null,
    @SerialName("per_page") val perPage: Int,
)