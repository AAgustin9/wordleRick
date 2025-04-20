package com.example.wordlerick.api

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun Api() {
    val viewModel = hiltViewModel<ApiViewModel>()
    val characters by viewModel.characters.collectAsStateWithLifecycle()
    val loading by viewModel.loading.collectAsStateWithLifecycle()
    val retry by viewModel.showRetry.collectAsStateWithLifecycle()

    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.verticalScroll(rememberScrollState()),
    ) {
        if (loading) {
            CircularProgressIndicator(
                color = Color.Gray,
                modifier = Modifier.size(48.dp)
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
            Image(
                painter = painterResource(character.image.length),
                contentDescription = "Character image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(60.dp),
            )
            Spacer(
                modifier = Modifier.size(10.dp)
            )
            Text(
                text = character.name,
                fontSize = 24.sp
            )
        }
        if(!character.alive) {
            Text("Dead")
        }
    }
    HorizontalDivider()
}