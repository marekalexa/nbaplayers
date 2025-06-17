package com.example.nbaplayers.ui.model.screen

import androidx.paging.PagingData
import com.example.nbaplayers.ui.model.PlayerUiModel
import com.example.nbaplayers.ui.model.TeamDetailUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * UI state for [TeamDetailScreen], including team info and a paginated list of players.
 */
data class TeamDetailScreenState(
    val team: TeamDetailUiModel? = null,
    val players: Flow<PagingData<PlayerUiModel>> = flowOf(PagingData.empty())
)
