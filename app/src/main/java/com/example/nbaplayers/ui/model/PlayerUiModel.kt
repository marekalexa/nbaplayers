package com.example.nbaplayers.ui.model

import androidx.annotation.DrawableRes
import com.example.nbaplayers.domain.model.Player

data class PlayerUiModel(
    val id: Int,
    val fullname: String,
    val position: String,
    val teamName: String,
    @DrawableRes val headshot: Int,
)

fun Player.toUiModel(): PlayerUiModel {
    return PlayerUiModel(
        id = id,
        fullname = "$firstName $lastName",
        position = position,
        headshot = getPlayerHeadshot(id),
        teamName = team?.name ?: "N/A",
    )
}
