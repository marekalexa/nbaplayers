package com.example.nbaplayers.ui.component

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

/**
 * A back arrow icon button, typically used for navigating up in the app.
 *
 * @param modifier Modifier to be applied to the button.
 * @param onBack Callback invoked when the button is clicked.
 */
@Composable
fun BackArrow(modifier: Modifier = Modifier, onBack: () -> Unit) {
    IconButton(
        onClick = onBack,
        modifier = modifier
            .padding(8.dp)
    ) {
        Icon(
            Icons.AutoMirrored.Rounded.ArrowBack,
            contentDescription = "Back",
            tint = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.size(24.dp)
        )
    }
}
