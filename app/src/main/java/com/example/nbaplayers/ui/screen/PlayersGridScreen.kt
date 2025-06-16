package com.example.nbaplayers.ui.screen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.nbaplayers.ui.component.PlayersGridList
import com.example.nbaplayers.ui.viewmodel.PlayersViewModel


@OptIn(ExperimentalFoundationApi::class, ExperimentalSharedTransitionApi::class)
@Composable
fun PlayersGridScreen(
    onPlayerClick: (Int) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: PlayersViewModel = hiltViewModel(),
) = with(sharedTransitionScope) {
    val screenState = viewModel.screenState.collectAsState()
    val players = screenState.value.players.collectAsLazyPagingItems()
    rememberLazyGridState()

    val isRefreshing = players.loadState.refresh is LoadState.Loading
    players.loadState.append
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            PlayersGridList(
                modifier = Modifier.weight(1f),
                players = players,
                onPlayerClick = onPlayerClick,
                sharedTransitionScope = this@with,
                animatedVisibilityScope = animatedVisibilityScope,
            )
        }

        if (isRefreshing) {
            LoaderIndicator()
        }
    }
}

@Composable
private fun LoaderIndicator() {
    Box(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background.copy(alpha = 0.8f)),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(Modifier.padding(16.dp))
    }
}