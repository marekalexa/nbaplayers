package com.example.nbaplayers.domain.model

data class Player(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val position: String,
    val height: String? = null,
    val weight: String? = null,
    val team: Team? = null,
)