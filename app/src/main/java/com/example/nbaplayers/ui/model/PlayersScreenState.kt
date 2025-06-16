package com.example.nbaplayers.ui.model

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

data class PlayersScreenState(
    val players: Flow<PagingData<PlayerUiModel>>,
)
