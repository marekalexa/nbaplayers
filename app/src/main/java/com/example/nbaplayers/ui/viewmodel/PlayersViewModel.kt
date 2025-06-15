package com.example.nbaplayers.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.nbaplayers.R
import com.example.nbaplayers.ui.model.PlayerUiModel
import com.example.nbaplayers.ui.model.PlayersScreenState
import com.example.nbaplayers.ui.model.TeamUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(
) : ViewModel() {

    val screenState: Flow<PlayersScreenState> = flow {
        emit(
            PlayersScreenState(
                isLoading = false,
                error = null,
                players = listOf(
                    PlayerUiModel(
                        id = 1,
                        firstName = "LeBron",
                        lastName = "James",
                        position = "F",
                        headshot = randomPlayerHeadshot(),
                        team = TeamUiModel(
                            id = 1,
                            fullName = "Los Angeles Lakers",
                            abbreviation = "LAL",
                            city = "Los Angeles",
                            conference = "West",
                            division = "Pacific",
                            name = "Lakers",
                            logo = randomLogo()
                        ),
                    ),
                    PlayerUiModel(
                        id = 2,
                        firstName = "LeBron",
                        lastName = "James",
                        position = "F",
                        headshot = randomPlayerHeadshot(),
                        team = TeamUiModel(
                            id = 1,
                            fullName = "Los Angeles Lakers",
                            abbreviation = "LAL",
                            city = "Los Angeles",
                            conference = "West",
                            division = "Pacific",
                            name = "Lakers",
                            logo = randomLogo()
                        ),
                    ),
                    PlayerUiModel(
                        id = 3,
                        firstName = "LeBron",
                        lastName = "James",
                        position = "F",
                        headshot = randomPlayerHeadshot(),
                        team = TeamUiModel(
                            id = 2,
                            fullName = "Los Angeles Lakers",
                            abbreviation = "LAL",
                            city = "Los Angeles",
                            conference = "West",
                            division = "Pacific",
                            name = "Lakers",
                            logo = randomLogo()
                        ),
                    ),
                    PlayerUiModel(
                        id = 4,
                        firstName = "LeBron",
                        lastName = "James",
                        position = "F",
                        headshot = randomPlayerHeadshot(),
                        team = TeamUiModel(
                            id = 3,
                            fullName = "Los Angeles Lakers",
                            abbreviation = "LAL",
                            city = "Los Angeles",
                            conference = "West",
                            division = "Pacific",
                            name = "Lakers",
                            logo = randomLogo()
                        ),
                    ),
                )
            )
        )
    }
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