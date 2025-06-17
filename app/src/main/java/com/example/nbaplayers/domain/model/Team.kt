package com.example.nbaplayers.domain.model

/**
 * Domain model representing an NBA team.
 *
 * @property id Unique identifier of the team.
 * @property abbreviation Abbreviated name (e.g., "LAL").
 * @property city City where the team is based.
 * @property conference Conference to which the team belongs (e.g., "West").
 * @property division Division within the conference (e.g., "Pacific").
 * @property fullName Full official name of the team (e.g., "Los Angeles Lakers").
 * @property name Team's short name (e.g., "Lakers").
 */
data class Team(
    val id: Int,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    val fullName: String,
    val name: String,
)
