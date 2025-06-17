package com.example.nbaplayers.ui.model

import androidx.annotation.DrawableRes
import com.example.nbaplayers.domain.model.Player

/**
 * UI model representing detailed information about an NBA player,
 * including team-related context and visual assets.
 *
 * @property id Unique identifier of the player.
 * @property fullname Full name of the player.
 * @property position Player's playing position (e.g., PG, SF).
 * @property teamName Name of the team the player belongs to.
 * @property headshot Drawable resource for the player's headshot.
 * @property height Player height in formatted string (e.g., 6'5").
 * @property weight Player weight in formatted string (e.g., 200 lbs).
 * @property teamCity City where the player's team is based.
 * @property conference NBA conference (East or West).
 * @property teamLogo Drawable resource for the team's logo.
 * @property teamId Identifier of the team (nullable).
 */
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
    val teamId: Int?,
)

/**
 * Maps a domain-level [Player] model to a UI-friendly [PlayerDetailUiModel].
 */
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
        teamId = team?.id,
    )
}

