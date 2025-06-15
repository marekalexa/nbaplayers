package com.example.nbaplayers.ui.model

import androidx.annotation.DrawableRes

data class PlayerUiModel(
    val id: Int,
    val fullname: String,
    val position: String,
    val teamName: String,
    @DrawableRes val headshot: Int,
)