package com.example.nbaplayers.ui.model

import androidx.annotation.DrawableRes
import com.example.nbaplayers.R

/**
 * Select a headshot picture for demonstration purposes
 */
@DrawableRes
fun getPlayerHeadshot(id: Int): Int {
    return when (id % 7) {
        0 -> R.drawable.player1
        1 -> R.drawable.player2
        2 -> R.drawable.player3
        3 -> R.drawable.player3
        4 -> R.drawable.player4
        5 -> R.drawable.player5
        else -> R.drawable.player6
    }
}

/**
 * Select a team logofor demonstration purposes
 */
@DrawableRes
fun getTeamLogo(id: Int): Int {
    return when (id % 9) {
        0 -> R.drawable.team1
        1 -> R.drawable.team2
        2 -> R.drawable.team3
        3 -> R.drawable.team4
        4 -> R.drawable.team5
        5 -> R.drawable.team6
        6 -> R.drawable.team7
        7 -> R.drawable.team8
        else -> R.drawable.team9
    }
}
