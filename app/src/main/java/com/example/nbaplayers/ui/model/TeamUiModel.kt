package com.example.nbaplayers.ui.model

import androidx.annotation.DrawableRes

/**
 * UI model for listing or summarizing NBA teams.
 *
 * @property id Unique identifier of the team.
 * @property logo Drawable resource ID for the team’s logo.
 * @property abbreviation Abbreviated team name (e.g., "BOS").
 * @property city City where the team is based.
 * @property conference Conference the team belongs to.
 * @property division Division the team belongs to.
 * @property fullName Full name of the team (e.g., "Boston Celtics").
 * @property name Short name of the team (e.g., "Celtics").
 */
data class TeamUiModel(
    val id: Int,
    @DrawableRes val logo: Int,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    val fullName: String,
    val name: String
)