package com.example.nbaplayers.ui.screen

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.nbaplayers.ui.component.ErrorBanner
import com.example.nbaplayers.ui.model.PlayerUiModel
import com.example.nbaplayers.ui.viewmodel.PlayersViewModel

private val CardHeight = 180.dp
private val CardPadding = 8.dp

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
    val gridState = rememberLazyGridState()

    val isRefreshing = players.loadState.refresh is LoadState.Loading
    val appendState = players.loadState.append
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                state = gridState,
                modifier = Modifier.weight(1f),
            ) {
                items(
                    count = players.itemCount,
                    key = players.itemKey { player -> "player_${player.id}" },
                    contentType = { "playerCard" },
                ) { index ->
                    val player = players[index]
                    if (player != null) {
                        PlayerCard(
                            player = player,
                            onClick = onPlayerClick,
                            sharedTransitionScope = this@with,
                            animatedVisibilityScope = animatedVisibilityScope,
                        )
                    } else {
                        PlayerCardPlaceholder()
                    }
                }

                // Add loading indicator at the end when loading more items
                if (appendState is LoadState.Loading) {
                    item(
                        key = "loading_indicator",
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }

                // Add error state at the end when loading fails
                if (appendState is LoadState.Error) {
                    item(
                        key = "error_banner",
                        span = { GridItemSpan(maxLineSpan) }
                    ) {
                        ErrorBanner(
                            message = appendState.error.localizedMessage ?: "Unknown error",
                            onRetry = players::retry,
                            modifier = Modifier
                                .padding(16.dp)
                                .height(CardHeight),
                        )
                    }
                }
            }
        }

        if (isRefreshing) {
            LoaderIndicator()
        }
    }
}

@OptIn(
    ExperimentalSharedTransitionApi::class,
    ExperimentalGlideComposeApi::class
)
@Composable
fun PlayerCard(
    modifier: Modifier = Modifier,
    player: PlayerUiModel,
    onClick: (Int) -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
) = with(sharedTransitionScope) {
    val imageKey = rememberSharedContentState("image_${player.id}")

    Card(
        modifier = modifier
            .padding(CardPadding)
            .height(CardHeight)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(onClick = { onClick(player.id) })
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
        ) {
            GlideImage(
                model = player.headshot,
                contentDescription = "${player.fullname} picture",
                modifier = Modifier
                    .size(56.dp)
                    .sharedElement(
                        sharedContentState = imageKey,
                        animatedVisibilityScope = animatedVisibilityScope
                    ),
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = player.fullname,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = player.teamName,
                style = MaterialTheme.typography.bodySmall
            )

            Text(
                text = player.position,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
private fun PlayerCardPlaceholder(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .padding(CardPadding)
            .height(CardHeight)
            .fillMaxWidth(),
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(modifier = Modifier.size(24.dp))
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