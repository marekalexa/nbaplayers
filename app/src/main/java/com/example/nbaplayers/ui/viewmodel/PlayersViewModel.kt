package com.example.nbaplayers.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.nbaplayers.R
import com.example.nbaplayers.domain.model.Player
import com.example.nbaplayers.domain.repository.PlayersRepository
import com.example.nbaplayers.ui.model.PlayerUiModel
import com.example.nbaplayers.ui.model.PlayersScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(
    repo: PlayersRepository
) : ViewModel() {

    /** Flow<PagingData<PlayerUiModel>> coming from the repo, already mapped
     *  from domain → UI models. */
    private val pagingFlow: Flow<PagingData<PlayerUiModel>> =
        repo.playersFlow()
            .map { paging ->
                paging.map { player ->
                    PlayerUiModel(
                        id = player.id,
                        fullname = "${player.firstName} ${player.lastName}",
                        position = player.position,
                        headshot = getPlayerHeadshot(player.id),
                        teamName = player.team.name,
                    )
                }
            }.cachedIn(viewModelScope)

    /** Exposed to the UI.  It wraps the *flow*, not a single page. */
    val screenState: StateFlow<PlayersScreenState> =
        MutableStateFlow(
            PlayersScreenState(players = pagingFlow)
        )
}

/**
 * Select a headshot picture for demonstration purposes
 */
private fun getPlayerHeadshot(id: Int): Int {
    return when (id % 4) {
        0 -> R.drawable.player1
        1 -> R.drawable.player2
        2 -> R.drawable.player3
        else -> R.drawable.player4
    }
}

private fun Player.toUiModel(): PlayerUiModel {
    return PlayerUiModel(
        id = id,
        fullname = "$firstName $lastName",
        position = position,
        headshot = getPlayerHeadshot(id),
        teamName = team.name,
    )
}