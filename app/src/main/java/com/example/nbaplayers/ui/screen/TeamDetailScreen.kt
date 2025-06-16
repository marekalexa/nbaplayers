package com.example.nbaplayers.ui.screen

import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.example.nbaplayers.ui.viewmodel.TeamDetailViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSharedTransitionApi::class,
    ExperimentalGlideComposeApi::class
)
@Composable
fun TeamDetailScreen(
    onBack: () -> Unit,
    viewModel: TeamDetailViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Rounded.ArrowBack, null)
                    }
                }
            )
        }
    ) { pad ->
        uiState.team?.let { team ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(pad),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {

                Card(
                    Modifier
                        .padding(horizontal = 16.dp)
                        .fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        Modifier
                            .padding(24.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        GlideImage(
                            model = team.logo,
                            contentDescription = "${team.fullName} logo",
                            modifier = Modifier
                                .size(120.dp)
                                .clip(MaterialTheme.shapes.extraLarge),
                            contentScale = ContentScale.Fit
                        )

                        Spacer(Modifier.height(12.dp))

                        Text(team.fullName, style = MaterialTheme.typography.headlineSmall)
                        Text(
                            "${team.city} • ${team.conference}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

            }
        }
    }
}
