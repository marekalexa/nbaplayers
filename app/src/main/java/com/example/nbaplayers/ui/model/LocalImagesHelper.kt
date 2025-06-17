package com.example.nbaplayers.ui.model

import androidx.annotation.DrawableRes
import com.example.nbaplayers.R

/**
 * Returns a demo player headshot resource ID based on the given player ID.
 * Used to visually differentiate mock or sample player data.
 *
 * @param id The player ID.
 * @return A drawable resource ID representing the headshot.
 */
@DrawableRes
fun getPlayerHeadshot(id: Int): Int {
    return when (id % 7) {
        0 -> R.drawable.player1
        1 -> R.drawable.player2
        2 -> R.drawable.player3
        3 -> R.drawable.player4
        4 -> R.drawable.player5
        5 -> R.drawable.player6
        else -> R.drawable.player7
    }
}

/**
 * Returns a demo team logo resource ID based on the given team ID.
 * Used to visually differentiate mock or sample team data.
 *
 * @param id The team ID.
 * @return A drawable resource ID representing the team logo.
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
