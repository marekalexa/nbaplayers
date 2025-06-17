package com.example.nbaplayers.data.remote.dto

import com.example.nbaplayers.data.local.entity.TeamEntity
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

fun TeamDto.toLocal() = TeamEntity(
    id = id,
    abbreviation = abbreviation,
    city = city,
    conference = conference,
    division = division,
    fullName = fullName,
    name = name
)