package com.example.nba.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.nba.R
import com.example.nba.data.model.Game
import com.example.nba.presentation.components.ExpandableSection
import com.example.nba.presentation.components.MatchInfoCard
import com.example.nba.presentation.components.PredictionCard
import com.example.nba.presentation.components.RecentFormCard
import com.example.nba.presentation.components.TeamComparisonCard

@Composable
fun ResultScreen(
    navController: NavHostController
) {

    // Şimdilik sahte veri kullanıyoruz.
    // API geldiğinde ViewModel üzerinden gelecek.

    val game = Game(
        homeTeam = "Los Angeles Lakers",
        awayTeam = "Boston Celtics",
        homeProbability = 68,
        awayProbability = 32,
        homeForm = "WWLWW",
        awayForm = "WLWLW",
        arena = "Crypto.com Arena",
        city = "Los Angeles",
        matchTime = "20:30",
        matchDate = "07 Aug 2026"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {

        Text(
            text = stringResource(R.string.prediction),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        PredictionCard(game)

        Spacer(modifier = Modifier.height(16.dp))

        ExpandableSection(
            title = stringResource(R.string.team_comparison)
        ) {
            TeamComparisonCard(game)
        }

        Spacer(modifier = Modifier.height(12.dp))

        ExpandableSection(
            title = stringResource(R.string.recent_form)
        ) {
            RecentFormCard(game)
        }

        Spacer(modifier = Modifier.height(12.dp))

        ExpandableSection(
            title = stringResource(R.string.match_information)
        ) {
            MatchInfoCard(game)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                navController.popBackStack()
            }
        ) {
            Text(
                text = "← ${stringResource(R.string.back)}"
            )
        }

    }

}