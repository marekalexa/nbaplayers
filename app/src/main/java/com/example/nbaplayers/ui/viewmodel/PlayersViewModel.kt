package com.example.nbaplayers.ui.viewmodel

import PlayersScreenState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.nbaplayers.R
import com.example.nbaplayers.domain.repository.PlayersRepository
import com.example.nbaplayers.ui.model.PlayerUiModel
import com.example.nbaplayers.ui.model.TeamUiModel
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
                        firstName = player.firstName,
                        lastName = player.lastName,
                        position = player.position,
                        headshot = randomPlayerHeadshot(),
                        team = TeamUiModel(
                            id = player.team.id,
                            fullName = player.team.fullName,
                            abbreviation = player.team.abbreviation,
                            city = player.team.city,
                            conference = player.team.conference,
                            division = player.team.division,
                            name = player.team.name,
                            logo = randomLogo()
                        )
                    )
                }
            }
            .cachedIn(viewModelScope)

    /** Exposed to the UI.  It wraps the *flow*, not a single page. */
    val screenState: StateFlow<PlayersScreenState> =
        MutableStateFlow(
            PlayersScreenState(players = pagingFlow)
        )
}


/**
 * Randomly select a logo for demonstration purposes
 */
private fun randomLogo(): Int {
    return when ((1..9).random()) {
        1 -> R.drawable.team1
        2 -> R.drawable.team2
        3 -> R.drawable.team3
        4 -> R.drawable.team4
        5 -> R.drawable.team5
        6 -> R.drawable.team6
        7 -> R.drawable.team7
        8 -> R.drawable.team8
        else -> R.drawable.team9
    }
}

/**
 * Randomly select a logo for demonstration purposes
 */
private fun randomPlayerHeadshot(): Int {
    return when ((1..4).random()) {
        1 -> R.drawable.player1
        2 -> R.drawable.player2
        3 -> R.drawable.player3
        else -> R.drawable.player4
    }
}