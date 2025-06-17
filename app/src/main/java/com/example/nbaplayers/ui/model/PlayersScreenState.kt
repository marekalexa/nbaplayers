package com.example.nbaplayers.ui.model

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

/**
 * Represents the UI state of the screen displaying a list of NBA players.
 *
 * @property players Flow of paginated [PlayerUiModel] instances.
 */
data class PlayersScreenState(
    val players: Flow<PagingData<PlayerUiModel>>,
)
