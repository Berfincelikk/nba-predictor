package com.example.nba.presentation.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.nba.R
import com.example.nba.data.model.Game
import com.example.nba.data.remote.RetrofitClient
import com.example.nba.presentation.components.ExpandableSection
import com.example.nba.presentation.components.MatchInfoCard
import com.example.nba.presentation.components.PredictionCard
import com.example.nba.presentation.components.RecentFormCard
import com.example.nba.presentation.components.TeamComparisonCard

@Composable
fun ResultScreen(
    navController: NavHostController,
    gameId: String
) {

    var game by remember { mutableStateOf<Game?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(gameId) {

        try {
            isLoading = true
            errorMessage = null

            val response = RetrofitClient.api.getGameDetail(gameId)

            game = Game(
                homeTeam = response.homeTeam,
                awayTeam = response.awayTeam,
                homeProbability = response.homeProbability,
                awayProbability = response.awayProbability,
                homeForm = response.homeForm,
                awayForm = response.awayForm,
                arena = response.arena,
                city = response.city,
                matchTime = response.matchTime,
                matchDate = response.matchDate
            )

        } catch (e: Exception) {
            errorMessage = e.message ?: "Tahmin alınamadı."
        } finally {
            isLoading = false
        }
    }

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

        when {

            isLoading -> {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 48.dp),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            errorMessage != null -> {

                Text(
                    text = errorMessage ?: "Bir hata oluştu.",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            game != null -> {

                val currentGame = game!!

                PredictionCard(currentGame)

                Spacer(modifier = Modifier.height(16.dp))

                ExpandableSection(
                    title = stringResource(R.string.team_comparison)
                ) {
                    TeamComparisonCard(currentGame)
                }

                Spacer(modifier = Modifier.height(12.dp))

                ExpandableSection(
                    title = stringResource(R.string.recent_form)
                ) {
                    RecentFormCard(currentGame)
                }

                Spacer(modifier = Modifier.height(12.dp))

                ExpandableSection(
                    title = stringResource(R.string.match_information)
                ) {
                    MatchInfoCard(currentGame)
                }
            }
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