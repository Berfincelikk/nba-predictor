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

        // 25 Ekim maçlarındaki takımlar
        "Utah Jazz" -> R.drawable.jazz
        "Los Angeles Lakers" -> R.drawable.lakers

        "Brooklyn Nets" -> R.drawable.nets
        "Indiana Pacers" -> R.drawable.pacers

        "New York Knicks" -> R.drawable.knicks
        "Orlando Magic" -> R.drawable.magic

        "Minnesota Timberwolves" -> R.drawable.timberwolves
        "Toronto Raptors" -> R.drawable.raptors

        "Oklahoma City Thunder" -> R.drawable.thunder
        "LA Clippers" -> R.drawable.clippers

        "Philadelphia 76ers" -> R.drawable.sixers
        "Detroit Pistons" -> R.drawable.pistons

        "Sacramento Kings" -> R.drawable.kings
        "Memphis Grizzlies" -> R.drawable.grizzlies

        // Daha önce eklediğimiz takımlar
        "Boston Celtics" -> R.drawable.celtics
        "Golden State Warriors" -> R.drawable.warriors
        "Phoenix Suns" -> R.drawable.suns
        "Miami Heat" -> R.drawable.heat
        "Milwaukee Bucks" -> R.drawable.bucks
        "Dallas Mavericks" -> R.drawable.mavericks
        "Denver Nuggets" -> R.drawable.nuggets

        // Tanınmayan takım gelirse Lakers göstermesin
        else -> R.drawable.logo
    }

    Image(
        painter = painterResource(id = logo),
        contentDescription = "$team logo",
        modifier = Modifier.size(64.dp)
    )
}