package com.example.nbaplayers.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.nbaplayers.domain.usecase.GetPlayersUseCase
import com.example.nbaplayers.ui.model.PlayerUiModel
import com.example.nbaplayers.ui.model.screen.PlayersListScreenState
import com.example.nbaplayers.ui.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

/**
 * ViewModel providing paginated player data to the [PlayersGridScreen].
 *
 * Supports optional flow caching for performance.
 */
@HiltViewModel
open class PlayersListViewModel @Inject constructor(
    getPlayersUseCase: GetPlayersUseCase
) : ViewModel() {
    /** Controls whether the paging flow should be cached in the ViewModel scope. */
    open val cachingEnabled: Boolean = true

    /** Flow<PagingData<PlayerUiModel>> coming from the use case, already mapped
     *  from domain -> UI models. */
    private val pagingFlow: Flow<PagingData<PlayerUiModel>> =
        getPlayersUseCase()
            .map { paging ->
                paging.map { player ->
                    player.toUiModel()
                }
            }.let { flow -> if (cachingEnabled) flow.cachedIn(viewModelScope) else flow }


    /**
     * Exposes paginated player data mapped into UI models.
     * This is wrapped in a state holder for the screen.
     */
    val screenState: StateFlow<PlayersListScreenState> =
        MutableStateFlow(
            PlayersListScreenState(players = pagingFlow)
        )
}
