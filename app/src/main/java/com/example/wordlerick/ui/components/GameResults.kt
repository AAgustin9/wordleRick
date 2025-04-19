package com.example.wordlerick.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.wordlerick.R
import com.example.wordlerick.ui.theme.PortalGreen

@Composable
fun GameResults(score: Int, onRestart: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(dimensionResource(id = R.dimen.spacing_16)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.game_over),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_16)))

        Text(
            text = stringResource(id = R.string.your_score),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_8)))

        Text(
            text = "$score",
            style = MaterialTheme.typography.displayLarge,
            fontWeight = FontWeight.Bold,
            color = PortalGreen
        )

        Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.spacing_32)))

        Button(
            onClick = onRestart,
            colors = ButtonDefaults.buttonColors(containerColor = PortalGreen),
            modifier = Modifier.padding(dimensionResource(id = R.dimen.spacing_16))
        ) {
            Text(
                text = stringResource(id = R.string.play_again),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.padding(
                    horizontal = dimensionResource(id = R.dimen.button_padding_horizontal),
                    vertical = dimensionResource(id = R.dimen.button_padding_vertical)
                )
            )
        }
    }
}