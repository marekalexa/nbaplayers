package com.example.nbaplayers.ui.model

data class PlayersScreenState(
    val isLoading: Boolean,
    val error: String? = null,
    val players: List<PlayerUiModel>,
)
