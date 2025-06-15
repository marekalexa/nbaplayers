package com.example.nbaplayers.ui.model

import androidx.annotation.DrawableRes

data class PlayerUiModel(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val position: String,
    val team: TeamUiModel,
    @DrawableRes val headshot: Int,
)