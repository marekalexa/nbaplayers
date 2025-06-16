package com.example.nbaplayers.ui.model

import androidx.annotation.DrawableRes
import com.example.nbaplayers.domain.model.Player

data class PlayerDetailUiModel(
    val id: Int,
    val fullname: String,
    val position: String,
    val teamName: String,
    @DrawableRes val headshot: Int,
    val height: String?,
    val weight: String?,
    val teamCity: String,
    val conference: String,
    @DrawableRes val teamLogo: Int,
)

fun Player.toDetailUiModel(): PlayerDetailUiModel {
    return PlayerDetailUiModel(
        id = id,
        fullname = "$firstName $lastName",
        position = position,
        headshot = getPlayerHeadshot(id),
        height = "${height?.replace("-", "'")}\"",
        weight = "$weight lbs",
        teamName = team?.name ?: "N/A",
        teamCity = team?.city ?: "N/A",
        conference = team?.conference ?: "N/A",
        teamLogo = getTeamLogo(team?.id ?: 0),
    )
}

