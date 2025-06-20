package com.example.nbaplayers.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.example.nbaplayers.ui.navigation.AppNavGraph
import com.example.nbaplayers.ui.theme.NBAPlayersTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main activity hosting the app's composable UI and navigation graph.
 *
 * Applies theming and edge-to-edge layout, and sets the root navigation container.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NBAPlayersTheme {
                Scaffold(Modifier.fillMaxSize()) { padding ->
                    AppNavGraph(modifier = Modifier.padding(padding))
                }
            }
        }
    }
}