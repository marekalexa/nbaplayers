package com.example.nbaplayers.ui.model

import androidx.annotation.DrawableRes

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