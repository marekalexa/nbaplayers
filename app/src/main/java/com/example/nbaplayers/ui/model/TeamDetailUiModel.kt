package com.example.nbaplayers.ui.model

import androidx.annotation.DrawableRes
import com.example.nbaplayers.domain.model.Team

data class TeamDetailUiModel(
    val id: Int,
    val fullName: String,
    val city: String,
    val conference: String,
    val division: String,
    val abbreviation: String,
    @DrawableRes val logo: Int,
)

fun Team.toUiModel(): TeamDetailUiModel = TeamDetailUiModel(
    id = id,
    fullName = fullName,
    city = city,
    conference = conference,
    division = division,
    abbreviation = abbreviation,
    logo = getTeamLogo(id),
)