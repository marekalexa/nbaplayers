package com.example.nbaplayers.data.remote.dto

import com.example.nbaplayers.data.local.entity.PlayerEntity
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Data Transfer Object representing a player from the balldontlie API.
 * Maps the JSON response from the API to a Kotlin data class.
 *
 * @property id Unique identifier for the player
 * @property firstName Player's first name
 * @property lastName Player's last name
 * @property position Player's position on the court
 * @property height Player's height (optional)
 * @property weight Player's weight (optional)
 * @property team TeamDto representing the player's team
 */
@Serializable
data class PlayerDto(
    val id: Int,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    val position: String,
    val height: String? = null,
    val weight: String? = null,
    val team: TeamDto
)

/**
 * Converts a PlayerDto to a PlayerEntity.
 *
 * @return PlayerEntity for local storage
 */
fun PlayerDto.toLocal() = PlayerEntity(
    id = id,
    firstName = firstName,
    lastName = lastName,
    position = position,
    height = height,
    weight = weight,
    teamId = team.id
)