package com.example.nbaplayers.ui.component

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionScope
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
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.nbaplayers.ui.model.PlayerUiModel


private val CardHeight = 180.dp
private val CardPadding = 8.dp

@ExperimentalSharedTransitionApi
@Composable
fun PlayersGridList(
    modifier: Modifier = Modifier,
    players: LazyPagingItems<PlayerUiModel>,
    onPlayerClick: ((Int) -> Unit)? = null,
    sharedTransitionScope: SharedTransitionScope? = null,
    animatedVisibilityScope: AnimatedVisibilityScope? = null,
) = with(sharedTransitionScope) {
    val appendState = players.loadState.append

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize()
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

@OptIn(
    ExperimentalSharedTransitionApi::class,
    ExperimentalGlideComposeApi::class
)
@Composable
fun PlayerCard(
    modifier: Modifier = Modifier,
    player: PlayerUiModel,
    onClick: ((Int) -> Unit)?,
    sharedTransitionScope: SharedTransitionScope?,
    animatedVisibilityScope: AnimatedVisibilityScope?,
) = with(sharedTransitionScope) {

    // Use shared element transition if available
    val sharedElementModifier = if (sharedTransitionScope != null &&
        animatedVisibilityScope != null
    ) {
        with(sharedTransitionScope) {
            Modifier.sharedElement(
                sharedContentState = rememberSharedContentState("image_${player.id}"),
                animatedVisibilityScope = animatedVisibilityScope
            )
        }
    } else Modifier

    Card(
        modifier = modifier
            .padding(CardPadding)
            .height(CardHeight)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    enabled = onClick != null,
                    onClick = { onClick?.invoke(player.id) }
                )
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
        ) {
            GlideImage(
                model = player.headshot,
                contentDescription = "${player.fullname} picture",
                modifier = Modifier
                    .size(56.dp)
                    .then(sharedElementModifier)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = player.fullname,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium
            )

            player.teamName?.let { safeTeamName ->
                Text(
                    text = safeTeamName,
                    style = MaterialTheme.typography.bodySmall
                )
            }

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
