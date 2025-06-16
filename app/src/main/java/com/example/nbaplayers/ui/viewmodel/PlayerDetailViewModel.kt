package com.example.nbaplayers.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nbaplayers.domain.repository.PlayersRepository
import com.example.nbaplayers.ui.model.PlayerDetailUiModel
import com.example.nbaplayers.ui.model.toDetailUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class PlayerDetailViewModel @Inject constructor(
    repo: PlayersRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val id: Int = checkNotNull(savedStateHandle["playerId"])

    val uiState: StateFlow<PlayerDetailScreenState> =
        repo.playerFlow(id)
            .map { PlayerDetailScreenState(it.toDetailUiModel()) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = PlayerDetailScreenState()
            )
}

data class PlayerDetailScreenState(val player: PlayerDetailUiModel? = null)
