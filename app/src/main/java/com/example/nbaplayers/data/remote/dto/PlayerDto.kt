package com.example.nbaplayers.data.remote.dto

import com.example.nbaplayers.domain.model.Player
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PlayerDto(
    val id: Int,
    @SerialName("first_name") val firstName: String,
    @SerialName("last_name") val lastName: String,
    val position: String,
    @SerialName("height_feet") val heightFeet: Int? = null,
    @SerialName("height_inches") val heightInches: Int? = null,
    @SerialName("weight_pounds") val weightPounds: Int? = null,
    val team: TeamDto
)

fun PlayerDto.toDomainModel() = Player(
    id = id,
    firstName = firstName,
    lastName = lastName,
    position = position,
    heightFeet = heightFeet,
    heightInches = heightInches,
    weightPounds = weightPounds,
    team = team.toDomainModel()
)