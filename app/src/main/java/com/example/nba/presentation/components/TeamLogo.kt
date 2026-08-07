package com.example.nba.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.nba.R

@Composable
fun TeamLogo(team: String) {

    val logo = when (team) {

        "Los Angeles Lakers" -> R.drawable.lakers
        "Boston Celtics" -> R.drawable.celtics
        "Golden State Warriors" -> R.drawable.warriors
        "Phoenix Suns" -> R.drawable.suns
        "Miami Heat" -> R.drawable.heat
        "Milwaukee Bucks" -> R.drawable.bucks
        "Dallas Mavericks" -> R.drawable.mavericks
        "Denver Nuggets" -> R.drawable.nuggets

        else -> R.drawable.lakers
    }

    Image(
        painter = painterResource(id = logo),
        contentDescription = team,
        modifier = Modifier.size(64.dp)
    )
}