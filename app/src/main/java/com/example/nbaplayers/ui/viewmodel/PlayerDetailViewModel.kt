package com.example.nbaplayers.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nbaplayers.domain.repository.PlayersRepository
import com.example.nbaplayers.ui.model.screen.PlayerDetailScreenState
import com.example.nbaplayers.ui.model.toDetailUiModel
import com.example.nbaplayers.ui.navigation.NavArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel responsible for loading a single NBA player's details.
 *
 * Extracts the player ID from [SavedStateHandle] and exposes a [StateFlow]
 * with a UI-ready model.
 */
@HiltViewModel
class PlayerDetailViewModel @Inject constructor(
    repo: PlayersRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val playerId: Int = checkNotNull(savedStateHandle[NavArgs.PLAYER_ID])

    val uiState: StateFlow<PlayerDetailScreenState> =
        repo.playerFlow(playerId)
            .map { PlayerDetailScreenState(it.toDetailUiModel()) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PlayerDetailScreenState()
            )
}
