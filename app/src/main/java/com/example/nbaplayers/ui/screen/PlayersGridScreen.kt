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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.nbaplayers.BuildConfig
import com.example.nbaplayers.R
import com.example.nbaplayers.ui.component.PlayersGridList
import com.example.nbaplayers.ui.model.PlayerUiModel
import com.example.nbaplayers.ui.theme.NBAPlayersTheme
import com.example.nbaplayers.ui.viewmodel.PlayersListViewModel

/**
 * Screen displaying a paginated grid of all NBA players.
 *
 * Supports pull-to-refresh and shared transitions to the detail screen.
 *
 * @param onPlayerClick Callback when a player card is clicked.
 * @param sharedTransitionScope Scope for shared transitions.
 * @param animatedVisibilityScope Scope for animated visibility.
 * @param viewModel ViewModel providing the player paging data.
 */
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
    viewModel: PlayersListViewModel = hiltViewModel(),
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

/**
 * Debug overlay for displaying scroll position and item count.
 *
 * Visible only in debug builds.
 *
 * @param gridState The grid's scroll state.
 * @param modifier Modifier for positioning/styling the overlay.
 */
@Composable
fun DebugPositionOverlay(gridState: LazyGridState, modifier: Modifier) {
    // recomposes only when *either* value changes
    val info by remember {
        derivedStateOf {
            "${gridState.firstVisibleItemIndex} / " +
                    "${gridState.layoutInfo.totalItemsCount}"
        }
    }

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

@OptIn(
    ExperimentalSharedTransitionApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Preview(showBackground = true)
@Composable
private fun PlayersGridListPreview() {
    NBAPlayersTheme {
        val fakePlayers = listOf(
            PlayerUiModel(1, "LeBron James", "SF", "Lakers", R.drawable.player1),
            PlayerUiModel(2, "Anthony Davis", "PF", "Lakers", R.drawable.player2),
            PlayerUiModel(3, "Rookie Player", "SG", "Lakers", R.drawable.player3),
            PlayerUiModel(4, "Anthony Davis", "PF", "Lakers", R.drawable.player4),
            PlayerUiModel(5, "Rookie Player", "SG", "Lakers", R.drawable.player5),
            PlayerUiModel(6, "Anthony Davis", "PF", "Lakers", R.drawable.player6),
            PlayerUiModel(7, "Rookie Player", "SG", "Lakers", R.drawable.player7),
            PlayerUiModel(8, "LeBron James", "SF", "Lakers", R.drawable.player1),
            PlayerUiModel(9, "Anthony Davis", "PF", "Lakers", R.drawable.player2),
            PlayerUiModel(10, "Rookie Player", "SG", "Lakers", R.drawable.player3),
            PlayerUiModel(11, "Anthony Davis", "PF", "Lakers", R.drawable.player4),
            PlayerUiModel(12, "Rookie Player", "SG", "Lakers", R.drawable.player5),
            PlayerUiModel(13, "Anthony Davis", "PF", "Lakers", R.drawable.player6),
            PlayerUiModel(14, "Rookie Player", "SG", "Lakers", R.drawable.player7),
        )
        val flow = remember { kotlinx.coroutines.flow.flowOf(PagingData.from(fakePlayers)) }
        val lazyItems = flow.collectAsLazyPagingItems()
        val gridState = rememberLazyGridState()

        PlayersGridList(
            modifier = Modifier.fillMaxSize(),
            players = lazyItems,
            gridState = gridState,
            onPlayerClick = { },
            sharedTransitionScope = null,
            animatedVisibilityScope = null
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun DebugPositionOverlayPreview() {
    NBAPlayersTheme {
        DebugPositionOverlay(
            gridState = rememberLazyGridState(),
            modifier = Modifier
        )
    }
}
