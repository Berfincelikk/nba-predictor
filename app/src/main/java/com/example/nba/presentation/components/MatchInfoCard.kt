package com.example.nba.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nba.data.model.Game

@Composable
fun MatchInfoCard(
    game: Game
) {

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "📅 Match Information",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("🏟 Arena : ${game.arena}")

            Spacer(modifier = Modifier.height(8.dp))

            Text("📍 City : ${game.city}")

            Spacer(modifier = Modifier.height(8.dp))

            Text("🕒 Time : ${game.matchTime}")

            Spacer(modifier = Modifier.height(8.dp))

            Text("📆 Date : ${game.matchDate}")

        }

    }

}