package com.example.nbaplayers.domain.model

data class Team(
    val id: Int,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    val fullName: String,
    val name: String,
)