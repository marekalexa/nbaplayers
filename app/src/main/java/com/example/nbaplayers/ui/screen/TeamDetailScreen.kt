package com.example.nbaplayers.ui.screen

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.nbaplayers.R
import com.example.nbaplayers.ui.component.BackArrow
import com.example.nbaplayers.ui.component.PlayersGridList
import com.example.nbaplayers.ui.model.PlayerUiModel
import com.example.nbaplayers.ui.model.TeamDetailUiModel
import com.example.nbaplayers.ui.theme.NBAPlayersTheme
import com.example.nbaplayers.ui.viewmodel.TeamDetailViewModel

/**
 * Screen showing detailed information about a selected NBA team.
 *
 * Includes team details and a paginated list of its players.
 *
 * @param onBack Called when the user taps the back arrow.
 * @param viewModel Injected ViewModel for team and player data.
 */
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalSharedTransitionApi::class
)
@Composable
fun TeamDetailScreen(
    onBack: () -> Unit,
    viewModel: TeamDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val lazyPlayers = uiState.players.collectAsLazyPagingItems()
    val gridState = rememberLazyGridState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Spacer(Modifier.height(64.dp))

            uiState.team?.let { TeamHeader(it) }

            PlayersGridList(
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp)
                    .fillMaxWidth(),
                players = lazyPlayers,
                gridState = gridState,
            )
        }

        BackArrow(modifier = Modifier.align(Alignment.TopStart), onBack = onBack)
    }
}

/**
 * Displays the team information in a card layout.
 *
 * Shows logo, name, city, conference, and division.
 *
 * @param team The team to display.
 */
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun TeamHeader(team: TeamDetailUiModel) {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            GlideImage(
                model = team.logo,
                contentDescription = "${team.fullName} logo",
                modifier = Modifier
                    .size(120.dp)
                    .clip(MaterialTheme.shapes.extraLarge),
                contentScale = ContentScale.Fit
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = team.fullName,
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Center
                )
                Card(
                    shape = MaterialTheme.shapes.small,
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = team.abbreviation,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            Text(
                text = "${team.city} • ${team.conference}",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Text(
                text = team.division,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@OptIn(ExperimentalSharedTransitionApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun TeamDetailScreenPreview() {
    val team = TeamDetailUiModel(
        id = 1,
        fullName = "Los Angeles Lakers",
        city = "Los Angeles",
        conference = "West",
        division = "Pacific",
        abbreviation = "LAL",
        logo = R.drawable.team1
    )

    val fakePlayers = listOf(
        PlayerUiModel(1, "LeBron James", "SF", "Lakers", R.drawable.player1),
        PlayerUiModel(2, "Anthony Davis", "PF", "Lakers", R.drawable.player2),
        PlayerUiModel(3, "Rookie Player", "SG", "Lakers", R.drawable.player3),
        PlayerUiModel(4, "Anthony Davis", "PF", "Lakers", R.drawable.player4),
        PlayerUiModel(5, "Rookie Player", "SG", "Lakers", R.drawable.player5),
        PlayerUiModel(6, "Anthony Davis", "PF", "Lakers", R.drawable.player6),
        PlayerUiModel(7, "Rookie Player", "SG", "Lakers", R.drawable.player7),
    )
    val flow = remember { kotlinx.coroutines.flow.flowOf(PagingData.from(fakePlayers)) }
    val lazyItems = flow.collectAsLazyPagingItems()

    NBAPlayersTheme {
        Box(Modifier.fillMaxSize()) {
            Column(Modifier.fillMaxSize()) {
                Spacer(Modifier.height(64.dp))

                // Header
                TeamHeader(team)

                // Our grid list
                PlayersGridList(
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .fillMaxWidth(),
                    players = lazyItems,
                    gridState = rememberLazyGridState(),
                )
            }

            // Back arrow in the corner
            BackArrow(
                modifier = Modifier.align(Alignment.TopStart),
                onBack = {}
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun TeamHeaderPreview() {
    TeamHeader(
        team = TeamDetailUiModel(
            id = 1,
            fullName = "Los Angeles Lakers",
            city = "Los Angeles",
            conference = "West",
            division = "Pacific",
            abbreviation = "LAL",
            logo = R.drawable.team1
        )
    )
}
