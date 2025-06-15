package com.example.nbaplayers.domain.model

data class Player(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val position: String,
    val heightFeet: Int? = null,
    val heightInches: Int? = null,
    val weightPounds: Int? = null,
    val team: Team,
)