package com.example.nbaplayers.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.nbaplayers.domain.repository.PlayersRepository
import com.example.nbaplayers.domain.repository.TeamsRepository
import com.example.nbaplayers.ui.model.PlayerUiModel
import com.example.nbaplayers.ui.model.TeamDetailUiModel
import com.example.nbaplayers.ui.model.toUiModel
import com.example.nbaplayers.ui.navigation.NavArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel that provides a team's detail and its associated players.
 *
 * Loads both team and paginated player data using the provided repositories.
 */
@HiltViewModel
class TeamDetailViewModel @Inject constructor(
    teamsRepo: TeamsRepository,
    playersRepo: PlayersRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val teamId: Int = checkNotNull(savedStateHandle[NavArgs.TEAM_ID])

    private val teamUiFlow = teamsRepo
        .teamFlow(teamId)
        .map { it.toUiModel() }

    private val playersUiFlow = playersRepo
        .teamPlayersFlow(teamId)
        .cachedIn(viewModelScope)
        .map { paging ->
            paging.map { it.toUiModel() }
        }

    /**
     * State representing both the team metadata and its players.
     */
    val uiState: StateFlow<TeamDetailScreenState> =
        combine(teamUiFlow, playersUiFlow) { team, _ ->
            TeamDetailScreenState(team, playersUiFlow)
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = TeamDetailScreenState()
            )
}


/**
 * UI state for [TeamDetailScreen], including team info and a paginated list of players.
 */
data class TeamDetailScreenState(
    val team: TeamDetailUiModel? = null,
    val players: Flow<PagingData<PlayerUiModel>> = flowOf(PagingData.empty())
)
