package com.example.nba.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nba.data.model.Game

@Composable
fun TeamComparisonCard(
    game: Game
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                text = "📊 Team Comparison",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            ComparisonRow(
                title = "Win Probability",
                homeValue = "${game.homeProbability}%",
                awayValue = "${game.awayProbability}%"
            )

            Divider()

            ComparisonRow(
                title = "Recent Form",
                homeValue = game.homeForm,
                awayValue = game.awayForm
            )

            Divider()

            ComparisonRow(
                title = "Prediction",
                homeValue = if (game.homeProbability > game.awayProbability) "⭐ Favorite" else "",
                awayValue = if (game.awayProbability > game.homeProbability) "⭐ Favorite" else ""
            )

        }

    }

}

@Composable
private fun ComparisonRow(

    title: String,

    homeValue: String,

    awayValue: String

) {

    Column(
        modifier = Modifier.padding(vertical = 10.dp)
    ) {

        Text(
            text = title,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(6.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(homeValue)

            Text(awayValue)

        }

    }

}