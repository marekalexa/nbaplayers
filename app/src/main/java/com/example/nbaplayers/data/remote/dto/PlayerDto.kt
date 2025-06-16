package com.example.nbaplayers.data.remote.dto

import com.example.nbaplayers.data.local.entity.PlayerEntity
import com.example.nbaplayers.domain.model.Player
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

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

fun PlayerDto.toDomainModel() = Player(
    id = id,
    firstName = firstName,
    lastName = lastName,
    position = position,
    height = height,
    weight = weight,
    team = team.toDomainModel()
)

fun PlayerDto.toLocal() = PlayerEntity(
    id = id,
    firstName = firstName,
    lastName = lastName,
    position = position,
    height = height,
    weight = weight,
    teamId = team.id
)