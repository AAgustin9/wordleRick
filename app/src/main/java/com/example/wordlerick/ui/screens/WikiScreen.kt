package com.example.wordlerick.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wordlerick.R

val mockCharacters = listOf(
        ShowCharacter(1, "Rick Sanchez", R.drawable.rick),
        ShowCharacter(2, "Morty Smith", R.drawable.morty),
        ShowCharacter(3, "Summer Smith", R.drawable.summer),
        ShowCharacter(4, "Beth Smith", R.drawable.beth),
        ShowCharacter(5, "Jerry Smith", R.drawable.jerry),
        ShowCharacter(6, "Birdperson", R.drawable.morty),
        ShowCharacter(7, "Squanchy", R.drawable.morty),
        ShowCharacter(8, "Mr. Meeseeks", R.drawable.morty),
        ShowCharacter(9, "Evil Morty", R.drawable.morty),
        ShowCharacter(10, "Mr. Poopybutthole", R.drawable.morty)
)

class EncyclopediaViewModel : ViewModel() {
    var characters by mutableStateOf(mockCharacters)
        private set

    fun loadCharacters(query: String = "") {
        characters = if (query.isEmpty()) {
            mockCharacters
        } else {
            mockCharacters.filter { it.name.contains(query, ignoreCase = true) }
        }
    }
}

@Composable
fun WikiScreen(viewModel: EncyclopediaViewModel = viewModel()) {
    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
    val characters = viewModel.characters

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        SearchBar(searchQuery) { newQuery ->
            searchQuery = newQuery
            viewModel.loadCharacters(newQuery.text)
        }
        Spacer(modifier = Modifier.height(16.dp))
        CharacterList(characters)
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
fun CharacterList(characters: List<ShowCharacter>) {
    LazyColumn {
        items(characters.size) { index ->
            CharacterCard(characters[index])
        }
    }
}

@Composable
fun CharacterCard(character: ShowCharacter) {
    Card(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(modifier = Modifier.padding(16.dp)) {
            Image(
                painter = painterResource(character.imageResId),
                contentDescription = "Character Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(64.dp)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = character.name, style = MaterialTheme.typography.bodyLarge)
                //Text(text = "${character.status} - ${character.species}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
