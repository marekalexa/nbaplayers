package com.example.nbaplayers.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.nbaplayers.ui.screen.PlayersGridScreen

@Composable
fun AppNavGraph(modifier: Modifier) {

    val navController = rememberNavController()
    NavHost(navController, startDestination = "players", modifier = modifier) {
        composable("players") {
            PlayersGridScreen()
        }
    }
}

