package com.example.nbaplayers.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nbaplayers.domain.repository.PlayersRepository
import com.example.nbaplayers.domain.repository.TeamsRepository
import com.example.nbaplayers.ui.model.TeamDetailUiModel
import com.example.nbaplayers.ui.model.toUiModel          // write trivial mapper
import com.example.nbaplayers.ui.navigation.NavArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class TeamDetailViewModel @Inject constructor(
    repo: TeamsRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val teamId: Int = checkNotNull(savedStateHandle[NavArgs.TEAM_ID])

    val uiState: StateFlow<TeamDetailScreenState> =
        repo.teamFlow(teamId)
            .map { TeamDetailScreenState(it.toUiModel()) }
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5_000),
                TeamDetailScreenState()
            )
}

data class TeamDetailScreenState(val team: TeamDetailUiModel? = null)
