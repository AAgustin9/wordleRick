package com.example.wordlerick.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.wordlerick.R

@Composable
fun CharacterImage() {
    Image(
        painter = painterResource(id = R.drawable.morty),
        contentDescription = "Character Image",
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
    )
}
