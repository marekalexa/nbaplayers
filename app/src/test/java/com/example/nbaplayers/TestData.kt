package com.example.nbaplayers

import com.example.nbaplayers.data.local.entity.toDomain
import com.example.nbaplayers.data.local.entity.toDomainModel
import com.example.nbaplayers.data.remote.dto.PlayerDto
import com.example.nbaplayers.data.remote.dto.PlayersResponseDto
import com.example.nbaplayers.data.remote.dto.ResponseMetadataDto
import com.example.nbaplayers.data.remote.dto.TeamDto
import com.example.nbaplayers.data.remote.dto.toLocal

object TestData {
    val teamDto1 = TeamDto(
        id = 1,
        name = "Lakers",
        city = "Los Angeles",
        conference = "West",
        abbreviation = "LAL",
        division = "Pacific",
        fullName = "Los Angeles Lakers"
    )

    val teamDto2 = TeamDto(
        id = 2,
        name = "Celtics",
        city = "Boston",
        conference = "East",
        abbreviation = "BOS",
        division = "Atlantic",
        fullName = "Boston Celtics"
    )

    val teamEntity1 = teamDto1.toLocal()

    val teamEntity2 = teamDto2.toLocal()

    val team1 = teamEntity1.toDomainModel()

    val team2 = teamEntity2.toDomainModel()

    val playerDto1 = PlayerDto(
        id = 1,
        firstName = "John",
        lastName = "Doe",
        position = "PG",
        height = "6\"5'",
        weight = "200 lbs",
        team = teamDto1
    )

    val playerDto2 = PlayerDto(
        id = 2,
        firstName = "Jane",
        lastName = "Smith",
        position = "SG",
        height = "6\"5'",
        weight = "200 lbs",
        team = teamDto2
    )

    val playerDto3 = PlayerDto(
        id = 3,
        firstName = "Mystery",
        lastName = "Player",
        position = "SF",
        height = null,
        weight = null,
        team = teamDto2
    )

    val playerEntity1 = playerDto1.toLocal()
    val playerEntity2 = playerDto2.toLocal()
    val playerEntity3 = playerDto3.toLocal()

    val player1 = playerEntity1.toDomain(teamEntity1)
    val player2 = playerEntity2.toDomain(teamEntity2)
    val player3 = playerEntity3.toDomain(null)

    val apiResponse1 = PlayersResponseDto(
        data = listOf(
            playerDto1
        ),
        meta = ResponseMetadataDto(nextCursor = null, perPage = 35)
    )

}