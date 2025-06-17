package com.example.nbaplayers.ui.model.screen

import androidx.paging.PagingData
import com.example.nbaplayers.ui.model.PlayerUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Represents the UI state of the screen displaying a list of NBA players.
 *
 * @property players Flow of paginated [PlayerUiModel] instances.
 */
data class PlayersListScreenState(
    val players: Flow<PagingData<PlayerUiModel>> = flowOf(PagingData.empty())
)
