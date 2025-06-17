package com.example.nbaplayers.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nbaplayers.domain.model.Team

/**
 * Entity class representing a team in the local database.
 * Maps to the 'teams' table and stores basic team information.
 *
 * @property id Unique identifier for the team
 * @property name Team's full name
 * @property abbreviation Team's abbreviated name
 * @property city City where the team is based
 */
@Entity(tableName = "teams")
data class TeamEntity(
    @PrimaryKey val id: Int,
    val fullName: String,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    val name: String
)

/**
 * Converts a TeamEntity to a domain Team model.
 *
 * @return Domain Team model
 */
fun TeamEntity.toDomainModel() = Team(
    id = id,
    fullName = fullName,
    abbreviation = abbreviation,
    city = city,
    conference = conference,
    division = division,
    name = name
)