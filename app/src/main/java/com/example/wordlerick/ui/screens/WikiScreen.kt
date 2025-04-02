package com.example.wordlerick.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.layout.ContentScale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

val mockCharacters = listOf(
    Character("Rick Sanchez", "https://rickandmortyapi.com/api/character/avatar/1.jpeg", "Alive", "Human"),
    Character("Morty Smith", "https://rickandmortyapi.com/api/character/avatar/2.jpeg", "Alive", "Human"),
    Character("Summer Smith", "https://rickandmortyapi.com/api/character/avatar/3.jpeg", "Alive", "Human"),
    Character("Beth Smith", "https://rickandmortyapi.com/api/character/avatar/4.jpeg", "Alive", "Human"),
    Character("Jerry Smith", "https://rickandmortyapi.com/api/character/avatar/5.jpeg", "Alive", "Human")
)

data class Character(val name: String, val image: String, val status: String, val species: String)

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
//            Image(
//                painter = rememberImagePainter(character.image),
//                contentDescription = "Character Image",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.size(64.dp)
//            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = character.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = "${character.status} - ${character.species}", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}
