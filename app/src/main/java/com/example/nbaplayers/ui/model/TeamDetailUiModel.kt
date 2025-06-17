package com.example.nbaplayers.ui.model

import androidx.annotation.DrawableRes
import com.example.nbaplayers.domain.model.Team

/**
 * UI model representing detailed information about an NBA team.
 *
 * @property id Unique identifier of the team.
 * @property fullName Full name of the team (e.g., "Los Angeles Lakers").
 * @property city City where the team is based.
 * @property conference NBA conference the team belongs to.
 * @property division NBA division the team belongs to.
 * @property abbreviation Team abbreviation (e.g., "LAL").
 * @property logo Drawable resource for the team’s logo.
 */
data class TeamDetailUiModel(
    val id: Int,
    val fullName: String,
    val city: String,
    val conference: String,
    val division: String,
    val abbreviation: String,
    @DrawableRes val logo: Int,
)

/**
 * Maps a domain-level [Team] to a UI-friendly [TeamDetailUiModel].
 */
fun Team.toUiModel(): TeamDetailUiModel = TeamDetailUiModel(
    id = id,
    fullName = fullName,
    city = city,
    conference = conference,
    division = division,
    abbreviation = abbreviation,
    logo = getTeamLogo(id),
)