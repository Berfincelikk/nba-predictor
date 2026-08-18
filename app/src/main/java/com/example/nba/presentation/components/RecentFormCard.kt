package com.example.nba.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nba.data.model.Game

@Composable
fun RecentFormCard(
    game: Game
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        shape = MaterialTheme.shapes.large
    ) {

        Column(
            modifier = Modifier.padding(18.dp)
        ) {

            Text(
                text = "📈 Recent Form",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(20.dp))

            FormTeamRow(
                teamName = game.homeTeam,
                form = game.homeForm
            )

            Spacer(modifier = Modifier.height(18.dp))

            FormTeamRow(
                teamName = game.awayTeam,
                form = game.awayForm
            )
        }
    }
}

@Composable
private fun FormTeamRow(
    teamName: String,
    form: String
) {

    val wins = form.count { it == 'W' }
    val losses = form.count { it == 'L' }
    val total = wins + losses

    val winPercentage = if (total > 0) {
        (wins * 100) / total
    } else {
        0
    }

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = teamName,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )

            Text(
                text = "$wins W · $losses L",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            form.forEach { result ->

                FormDot(
                    isWin = result == 'W'
                )

                Text(
                    text = result.toString(),
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "$winPercentage% win rate",
            style = MaterialTheme.typography.bodySmall,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
private fun FormDot(
    isWin: Boolean
) {

    val dotColor = if (isWin) {
        Color(0xFF4CAF50)
    } else {
        Color(0xFFE53935)
    }

    Spacer(
        modifier = Modifier
            .size(10.dp)
            .background(
                color = dotColor,
                shape = CircleShape
            )
    )
}