package com.example.nbaplayers.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.nbaplayers.ui.screen.PlayerDetailScreen
import com.example.nbaplayers.ui.screen.PlayersGridScreen

@OptIn(ExperimentalAnimationApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun AppNavGraph(modifier: Modifier) {
    SharedTransitionLayout(modifier) {
        val navController = rememberNavController()
        NavHost(navController, startDestination = "players") {
            composable("players") {
                PlayersGridScreen(
                    onPlayerClick = { playerId -> navController.navigate("player/$playerId") },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this
                )
            }
            composable(
                route = "player/{playerId}",
                arguments = listOf(navArgument("playerId") { type = NavType.IntType })
            ) { back ->
                val id = back.arguments!!.getInt("playerId")

                PlayerDetailScreen(
                    playerId = id,
                    onBack = { navController.popBackStack() },
                    sharedTransitionScope = this@SharedTransitionLayout,
                    animatedVisibilityScope = this
                )
            }
        }
    }
}
