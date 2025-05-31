package com.example.wordlerick.api

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.wordlerick.ui.theme.size24
import com.example.wordlerick.ui.theme.sizeBig3
import com.example.wordlerick.ui.theme.sizeBig4
import com.example.wordlerick.ui.theme.smallerIntermediate

@Composable
fun Api() {
    val viewModel = hiltViewModel<ApiViewModel>()
    val characters by viewModel.characters.collectAsStateWithLifecycle()
    val loading by viewModel.loading.collectAsStateWithLifecycle()
    val retry by viewModel.showRetry.collectAsStateWithLifecycle()

    Column(
        verticalArrangement = Arrangement.spacedBy(smallerIntermediate),
        modifier = Modifier.verticalScroll(rememberScrollState()),
    ) {
        if (loading) {
            CircularProgressIndicator(
                color = Color.Gray,
                modifier = Modifier.size(sizeBig3)
            )
        } else if (retry) {
            Text(
                "There was an error"
            )
            Button(
                onClick = viewModel::retryApiCall
            ) {
                Text(
                    "Retry"
                )
            }
        } else {
            characters.forEach {
                CharacterCard(it)
            }
        }
    }
}

@Composable
fun CharacterCard(
    character: Character,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AsyncImage(
                model = character.image,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(sizeBig4),
            )
            Spacer(
                modifier = Modifier.size(smallerIntermediate)
            )
            Text(
                text = character.name,
                fontSize = size24
            )
        }
        if(!character.alive) {
            Text("Dead")
        }
    }
    HorizontalDivider()
}