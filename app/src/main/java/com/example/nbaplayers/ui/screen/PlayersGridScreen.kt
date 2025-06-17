package com.example.nbaplayers.ui.screen

import android.util.Log
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.LazyGridState
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.nbaplayers.BuildConfig
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
    val gridState = rememberLazyGridState()

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
                    gridState = gridState,
                    onPlayerClick = onPlayerClick,
                    sharedTransitionScope = this@with,
                    animatedVisibilityScope = animatedVisibilityScope,
                )
            }
        }
        if (BuildConfig.DEBUG) {
            DebugPositionOverlay(gridState, modifier = Modifier.align(Alignment.BottomEnd))
        }
    }
}

@Composable
fun DebugPositionOverlay(gridState: LazyGridState, modifier: Modifier) {
    // recomposes only when *either* value changes
    val info by remember {
        derivedStateOf {
            "${gridState.firstVisibleItemIndex} / " +
                    "${gridState.layoutInfo.totalItemsCount}"
        }
    }

    Log.e(
        "GridState",
        "First visible item index: ${gridState.firstVisibleItemIndex}, Total items: ${gridState.layoutInfo.totalItemsCount}"
    )
    Text(
        text = info,
        modifier = modifier
            .padding(8.dp)
            .background(Color.Black.copy(0.6f), RoundedCornerShape(4.dp))
            .padding(horizontal = 6.dp, vertical = 2.dp),
        color = Color.White,
        style = MaterialTheme.typography.labelMedium
    )
}