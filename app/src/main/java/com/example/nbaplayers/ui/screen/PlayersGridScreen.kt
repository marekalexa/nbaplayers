package com.example.nbaplayers.ui.screen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.nbaplayers.ui.component.PlayersGridList
import com.example.nbaplayers.ui.viewmodel.PlayersViewModel

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalSharedTransitionApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun PlayersGridScreen(
    onPlayerClick: (Int) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: PlayersViewModel = hiltViewModel(),
) = with(sharedTransitionScope) {
    val players = viewModel.screenState.collectAsState().value.players.collectAsLazyPagingItems()
    val refreshing = players.loadState.refresh is LoadState.Loading
    val pullState = rememberPullToRefreshState()

    players.loadState.append
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            PullToRefreshBox(
                isRefreshing = refreshing,
                state = pullState,
                onRefresh = { players.refresh() },
                modifier = Modifier.fillMaxSize()
            ) {
                PlayersGridList(
                    modifier = Modifier.fillMaxSize(),
                    players = players,
                    onPlayerClick = onPlayerClick,
                    sharedTransitionScope = this@with,
                    animatedVisibilityScope = animatedVisibilityScope,
                )
            }
        }
    }
}
