package com.example.nba.presentation.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nba.data.model.Game

@Composable
fun TeamComparisonCard(
    game: Game
) {

    val homeIsFavorite = game.homeProbability > game.awayProbability

    val favoriteTeam = if (homeIsFavorite) {
        game.homeTeam
    } else {
        game.awayTeam
    }

    val favoriteProbability = if (homeIsFavorite) {
        game.homeProbability
    } else {
        game.awayProbability
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        ),
        shape = MaterialTheme.shapes.large
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            // Başlık
            Text(
                text = "📊 Team Comparison",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Win Probability
            ComparisonRow(
                title = "Win Probability",
                homeValue = "${game.homeProbability}%",
                awayValue = "${game.awayProbability}%"
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp)
            )

            // Recent Form
            ComparisonRow(
                title = "Recent Form",
                homeValue = game.homeForm,
                awayValue = game.awayForm
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 4.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Prediction
            Text(
                text = "Prediction",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "⭐",
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.width(10.dp))

                Column {

                    Text(
                        text = favoriteTeam,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(2.dp))

                    Text(
                        text = "$favoriteProbability% win probability",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))
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
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = homeValue,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )

            Text(
                text = awayValue,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
        }
    }
}