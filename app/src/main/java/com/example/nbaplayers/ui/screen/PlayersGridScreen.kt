package com.example.nbaplayers.ui.screen

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.nbaplayers.ui.model.PlayerUiModel
import com.example.nbaplayers.ui.viewmodel.PlayersViewModel

private val CardHeight = 180.dp
private val CardPadding = 8.dp

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PlayersGridScreen(
    viewModel: PlayersViewModel = hiltViewModel()
) {
    val state by viewModel.screenState.collectAsState()
    val players = state.players.collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 140.dp),
        modifier = Modifier.fillMaxSize(),
    ) {
        items(players.itemCount) { index ->
            players[index]?.let { player ->
                PlayerCard(
                    player = player,
                    onClick = { /* todo */ }
                )
            }
        }

        when (val stateAppend = players.loadState.append) {
            is LoadState.Loading ->
                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    LoaderIndicator()
                }

            is LoadState.Error ->
                item(span = { GridItemSpan(maxCurrentLineSpan) }) {
                    Text(
                        "Error: ${stateAppend.error.localizedMessage}",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }

            else -> Unit
        }
    }
}


@OptIn(
    ExperimentalSharedTransitionApi::class,
    ExperimentalGlideComposeApi::class
)
@Composable
fun PlayerCard(
    player: PlayerUiModel,
    onClick: () -> Unit,
) {


    Card(
        modifier = Modifier
            .padding(CardPadding)
            .height(CardHeight)
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically)
        ) {

            GlideImage(
                model = player.headshot,
                contentDescription = "${player.fullname} picture",
                modifier = Modifier.size(56.dp)
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = player.fullname,
                modifier = Modifier
                    .fillMaxWidth(),
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
private fun LoaderIndicator() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator(Modifier.padding(16.dp))
    }
}

