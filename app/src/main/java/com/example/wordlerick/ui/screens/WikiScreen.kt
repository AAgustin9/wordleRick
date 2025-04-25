package com.example.wordlerick.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wordlerick.api.ApiViewModel
import com.example.wordlerick.api.Character
import coil3.compose.rememberAsyncImagePainter
import com.example.wordlerick.ui.theme.defaultSize
import com.example.wordlerick.ui.theme.halfDefault
import com.example.wordlerick.ui.theme.sizeBig5

@Composable
fun WikiScreen(
    viewModel: ApiViewModel = hiltViewModel(),
    onCharacterClick: (Character) -> Unit
) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val filteredCharacters by viewModel.filteredCharacters.collectAsStateWithLifecycle()
    val loading by viewModel.loading.collectAsStateWithLifecycle()
    val showRetry by viewModel.showRetry.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().padding(defaultSize)) {
        SearchBar(searchQuery) { newQuery ->
            searchQuery = newQuery
            viewModel.filterCharacters(newQuery.text)
        }
        Spacer(modifier = Modifier.height(defaultSize))
        
        if (loading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        } else if (showRetry) {
            Button(
                onClick = { viewModel.retryApiCall() },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text("Retry")
            }
        } else {
            CharacterList(
                characters = filteredCharacters,
                onCharacterClick = onCharacterClick
            )
        }
    }
}

@Composable
fun SearchBar(searchQuery: TextFieldValue, onQueryChange: (TextFieldValue) -> Unit) {
    OutlinedTextField(
        value = searchQuery,
        onValueChange = onQueryChange,
        modifier = Modifier.fillMaxWidth(),
        placeholder = { Text("Search characters...") },
        singleLine = true,
        shape = RoundedCornerShape(halfDefault),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline
        )
    )
}

@Composable
fun CharacterList(
    characters: List<Character>,
    onCharacterClick: (Character) -> Unit
) {
    LazyColumn {
        items(characters.size) { index ->
            CharacterCard(
                character = characters[index],
                onClick = { onCharacterClick(characters[index]) }
            )
        }
    }
}

@Composable
fun CharacterCard(
    character: Character,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(halfDefault)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(halfDefault),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(defaultSize)) {
            Image(
                painter = rememberAsyncImagePainter(character.image),
                contentDescription = "Character Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(sizeBig5)
            )
            Spacer(modifier = Modifier.width(defaultSize))
            Column {
                Text(text = character.name, style = MaterialTheme.typography.bodyLarge)
                Text(
                    text = if (character.alive) "Alive" else "Dead",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
