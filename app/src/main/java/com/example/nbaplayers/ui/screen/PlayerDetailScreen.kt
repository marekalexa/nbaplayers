package com.example.nbaplayers.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.rounded.FitnessCenter
import androidx.compose.material.icons.rounded.Numbers
import androidx.compose.material.icons.rounded.SportsBasketball
import androidx.compose.material.icons.rounded.Straighten
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.nbaplayers.R
import com.example.nbaplayers.ui.component.BackArrow
import com.example.nbaplayers.ui.model.PlayerDetailUiModel
import com.example.nbaplayers.ui.theme.NBAPlayersTheme
import com.example.nbaplayers.ui.viewmodel.PlayerDetailViewModel

/**
 * Screen displaying the details of a selected NBA player.
 *
 * Includes shared element transitions, profile stats, and team info.
 *
 * @param onBack Called when the user taps the back button.
 * @param sharedTransitionScope Provides the scope for shared element transitions.
 * @param animatedVisibilityScope Used to animate visibility transitions.
 * @param viewModel Injected view model that provides player data.
 * @param onTeamClick Called when the user taps the team card.
 */
@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalSharedTransitionApi::class,
    ExperimentalGlideComposeApi::class
)
@Composable
fun PlayerDetailScreen(
    onBack: () -> Unit,
    sharedTransitionScope: SharedTransitionScope,
    animatedVisibilityScope: AnimatedVisibilityScope,
    viewModel: PlayerDetailViewModel = hiltViewModel(),
    onTeamClick: (Int) -> Unit,
) = with(sharedTransitionScope) {

    val uiState by viewModel.uiState.collectAsState()
    Box(
        Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            uiState.player?.let { player ->
                Spacer(Modifier.height(64.dp))
                HeroSection(
                    player = player,
                    animatedVisibilityScope = animatedVisibilityScope,
                    sharedTransitionScope = sharedTransitionScope
                )
                Spacer(Modifier.height(16.dp))
                ProfileGridSection(player = player)
                Spacer(Modifier.height(16.dp))
                TeamCardSection(player = player, onTeamClick = onTeamClick)
            }
        }

        BackArrow(modifier = Modifier.align(Alignment.TopStart), onBack = onBack)
    }
}

/**
 * Displays the player's avatar, name, and team in a styled card.
 */
@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalGlideComposeApi::class)
@Composable
private fun HeroSection(
    player: PlayerDetailUiModel,
    animatedVisibilityScope: AnimatedVisibilityScope,
    sharedTransitionScope: SharedTransitionScope
) = with(sharedTransitionScope) {

    val imageKey = rememberSharedContentState("image_${player.id}")

    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            GlideImage(
                model = player.headshot,
                contentDescription = "${player.fullname} photo",
                modifier = Modifier
                    .size(160.dp)
                    .clip(MaterialTheme.shapes.extraLarge)
                    .sharedElement(imageKey, animatedVisibilityScope),
                contentScale = ContentScale.Fit
            )
            Spacer(Modifier.height(12.dp))
            Text(
                text = player.fullname,
                style = MaterialTheme.typography.headlineSmall,
                textAlign = TextAlign.Center
            )
            Text(
                text = player.teamName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * Displays a responsive grid of player statistics (position, height, etc.).
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ProfileGridSection(player: PlayerDetailUiModel) {
    Text(
        text = "Profile",
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(start = 24.dp, top = 8.dp, bottom = 4.dp)
    )

    val statCards = listOf(
        Triple(Icons.Rounded.SportsBasketball, "Position", player.position),
        Triple(Icons.Rounded.Straighten, "Height", player.height),
        Triple(Icons.Rounded.FitnessCenter, "Weight", player.weight),
        Triple(Icons.Rounded.Numbers, "ID", player.id.toString())
    )

    FlowRow(
        maxItemsInEachRow = 2,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        statCards.forEach { (icon, label, value) ->
            ElevatedCard(
                modifier = Modifier
                    .weight(1f, fill = true)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(
                            text = label,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            text = value ?: "N/A",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}

/**
 * Displays the player's team in a card format, clickable to navigate to team details.
 */
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
private fun TeamCardSection(player: PlayerDetailUiModel, onTeamClick: (Int) -> Unit) {
    Spacer(Modifier.height(16.dp))
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .clickable { player.teamId?.let { onTeamClick(it) } },
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                GlideImage(
                    model = player.teamLogo,
                    contentDescription = "${player.teamName} logo",
                    modifier = Modifier
                        .size(56.dp)
                        .clip(MaterialTheme.shapes.medium),
                    contentScale = ContentScale.Fit
                )
                Spacer(Modifier.width(16.dp))
                Column {
                    Text(player.teamName, style = MaterialTheme.typography.bodyLarge)
                    Text(
                        "${player.teamCity} • ${player.conference}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = "Go to team",
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}


@OptIn(ExperimentalSharedTransitionApi::class, ExperimentalGlideComposeApi::class)
@Preview(showBackground = true)
@Composable
private fun HeroSectionPreview() {
    NBAPlayersTheme {
        SharedTransitionLayout(Modifier.size(500.dp)) {
            AnimatedVisibility(visible = true) {
                val sample = PlayerDetailUiModel(
                    id = 23,
                    fullname = "LeBron James",
                    position = "SF",
                    headshot = R.drawable.player1,
                    height = "6'9\"",
                    weight = "250 lbs",
                    teamName = "Lakers",
                    teamCity = "Los Angeles",
                    conference = "West",
                    teamLogo = R.drawable.team1,
                    teamId = 1
                )
                HeroSection(
                    player = sample,
                    animatedVisibilityScope = this,
                    sharedTransitionScope = this@SharedTransitionLayout
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ProfileGridSectionPreview() {
    NBAPlayersTheme {
        Column(
            modifier = Modifier.height(300.dp)
        ) {
            val sample = PlayerDetailUiModel(
                id = 23,
                fullname = "LeBron James",
                position = "SF",
                headshot = R.drawable.player1,
                height = "6'9\"",
                weight = "250 lbs",
                teamName = "Lakers",
                teamCity = "Los Angeles",
                conference = "West",
                teamLogo = R.drawable.team1,
                teamId = 1
            )
            ProfileGridSection(player = sample)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TeamCardSectionPreview() {
    NBAPlayersTheme {
        val sample = PlayerDetailUiModel(
            id = 23,
            fullname = "LeBron James",
            position = "SF",
            headshot = R.drawable.player1,
            height = "6'9\"",
            weight = "250 lbs",
            teamName = "Lakers",
            teamCity = "Los Angeles",
            conference = "West",
            teamLogo = R.drawable.team1,
            teamId = 1
        )
        TeamCardSection(player = sample, onTeamClick = {})
    }
}
