package com.example.wordlerick.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import coil.compose.AsyncImage
import com.example.wordlerick.ui.theme.biggerIntermediate
import com.example.wordlerick.ui.theme.defaultSize
import com.example.wordlerick.ui.theme.halfDefault
import com.example.wordlerick.ui.theme.size24
import com.example.wordlerick.ui.theme.sizeBig1
import com.example.wordlerick.ui.theme.sizeExtraBig

@Composable
fun QuestionCard(
    imageUrl: String,
    question: String
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .padding(vertical = defaultSize),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = halfDefault),
        shape = RoundedCornerShape(defaultSize)
    ) {
        Column(
            modifier = Modifier.padding(sizeBig1),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(defaultSize),
        ) {
            Text(
                text = question,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                fontSize = size24,
                color = MaterialTheme.colorScheme.primary
            )

            AsyncImage(
                model = imageUrl,
                contentDescription = "Character Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(sizeExtraBig)
                    .padding(bottom = defaultSize)
                    .clip(RoundedCornerShape(biggerIntermediate))
            )

        }
    }
}