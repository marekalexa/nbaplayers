package com.example.nbaplayers.data.remote.dto

import com.example.nbaplayers.data.local.entity.TeamEntity
import com.example.nbaplayers.domain.model.Team
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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

fun TeamDto.toDomainModel() = Team(
    id = id,
    abbreviation = abbreviation,
    city = city,
    conference = conference,
    division = division,
    fullName = fullName,
    name = name
)

fun TeamDto.toLocal() = TeamEntity(
    id = id,
    abbreviation = abbreviation,
    city = city,
    conference = conference,
    division = division,
    fullName = fullName,
    name = name
)