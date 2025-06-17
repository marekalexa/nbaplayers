package com.example.nbaplayers.ui.model

import androidx.annotation.DrawableRes
import com.example.nbaplayers.domain.model.Player

/**
 * Lightweight UI model for displaying brief information about a player
 * in a list or grid format.
 *
 * @property id Unique identifier of the player.
 * @property fullname Full name of the player.
 * @property position Player's on-court position.
 * @property teamName Optional name of the player's team.
 * @property headshot Drawable resource ID representing the player's headshot.
 */
data class PlayerUiModel(
    val id: Int,
    val fullname: String,
    val position: String,
    val teamName: String?,
    @DrawableRes val headshot: Int,
)

/**
 * Maps a domain-level [Player] model to a [PlayerUiModel] used for lists or grids.
 */
fun Player.toUiModel(): PlayerUiModel {
    return PlayerUiModel(
        id = id,
        fullname = "$firstName $lastName",
        position = position,
        headshot = getPlayerHeadshot(id),
        teamName = team?.name,
    )
}
