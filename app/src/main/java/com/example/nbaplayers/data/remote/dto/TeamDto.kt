package com.example.nbaplayers.data.remote.dto

import com.example.nbaplayers.data.local.entity.TeamEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object representing a team from the balldontlie API.
 * Maps the JSON response from the API to a Kotlin data class.
 *
 * @property id Unique identifier for the team
 * @property fullName Team's full name
 * @property abbreviation Team's abbreviated name
 * @property city City where the team is based
 * @property conference Team's conference (e.g., "East", "West")
 * @property division Team's division (e.g., "Atlantic", "Pacific")
 * @property name Team's short name
 */
@Serializable
data class TeamDto(
    val id: Int,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    @SerialName("full_name") val fullName: String,
    val name: String
)

/**
 * Converts a TeamDto to a TeamEntity.
 *
 * @return TeamEntity for local storage
 */
fun TeamDto.toLocal() = TeamEntity(
    id = id,
    abbreviation = abbreviation,
    city = city,
    conference = conference,
    division = division,
    fullName = fullName,
    name = name
)