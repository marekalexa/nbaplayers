package com.example.nbaplayers.domain.model

/**
 * Domain model representing an NBA player.
 *
 * @property id Unique identifier of the player.
 * @property firstName First name of the player.
 * @property lastName Last name of the player.
 * @property position Player's position (e.g., PG, SG, etc.).
 * @property height Optional height of the player (e.g., "6'5\"").
 * @property weight Optional weight of the player (e.g., "200 lbs").
 * @property team Optional team to which the player belongs.
 */
data class Player(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val position: String,
    val height: String? = null,
    val weight: String? = null,
    val team: Team? = null,
)
