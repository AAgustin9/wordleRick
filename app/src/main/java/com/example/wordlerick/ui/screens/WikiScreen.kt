package com.example.wordlerick.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.layout.ContentScale
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.wordlerick.api.ApiViewModel
import com.example.wordlerick.api.Character
import coil3.compose.rememberAsyncImagePainter

//val mockCharacters = listOf(
//        ShowCharacter(1, "Rick Sanchez", R.drawable.rick),
//        ShowCharacter(2, "Morty Smith", R.drawable.morty),
//        ShowCharacter(3, "Summer Smith", R.drawable.summer),
//        ShowCharacter(4, "Beth Smith", R.drawable.beth),
//        ShowCharacter(5, "Jerry Smith", R.drawable.jerry),
//        ShowCharacter(6, "Birdperson", R.drawable.morty),
//        ShowCharacter(7, "Squanchy", R.drawable.morty),
//        ShowCharacter(8, "Mr. Meeseeks", R.drawable.morty),
//        ShowCharacter(9, "Evil Morty", R.drawable.morty),
//        ShowCharacter(10, "Mr. Poopybutthole", R.drawable.morty)
//)

@Composable
fun WikiScreen(viewModel: ApiViewModel = hiltViewModel()) {
    //var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val characters by viewModel.characters.collectAsStateWithLifecycle()
    val loading by viewModel.loading.collectAsStateWithLifecycle()
    val showRetry by viewModel.showRetry.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
//        SearchBar(searchQuery) { newQuery ->
//            searchQuery = newQuery
//             TODO: Implement search filtering
//        }
        Spacer(modifier = Modifier.height(16.dp))
        
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
            CharacterList(characters)
        }
    }
}

@Composable
fun SearchBar(searchQuery: TextFieldValue, onQueryChange: (TextFieldValue) -> Unit) {
    BasicTextField(
        value = searchQuery,
        onValueChange = onQueryChange,
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        textStyle = LocalTextStyle.current.copy(color = Color.Black)
    )
}

@Composable
fun CharacterList(characters: List<Character>) {
    LazyColumn {
        items(characters.size) { index ->
            CharacterCard(characters[index])
        }
    }
}

@Composable
fun CharacterCard(character: Character) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(character.image),
                contentDescription = "Character Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
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
