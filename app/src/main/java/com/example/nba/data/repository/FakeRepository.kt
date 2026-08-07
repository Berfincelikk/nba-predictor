package com.example.nba.data.repository

import com.example.nba.data.model.Game

object FakeRepository {

    fun getGames(): List<Game> {

        return listOf(

            Game(
                homeTeam = "Los Angeles Lakers",
                awayTeam = "Boston Celtics",
                homeProbability = 68,
                awayProbability = 32,
                homeForm = "WWLWW",
                awayForm = "WLWLW",
                arena = "Crypto.com Arena",
                city = "Los Angeles",
                matchTime = "20:30",
                matchDate = "06 Aug 2026"
            ),

            Game(
                homeTeam = "Golden State Warriors",
                awayTeam = "Phoenix Suns",
                homeProbability = 54,
                awayProbability = 46,
                homeForm = "WWWLW",
                awayForm = "LWWLL",
                arena = "Chase Center",
                city = "San Francisco",
                matchTime = "22:00",
                matchDate = "06 Aug 2026"
            ),

            Game(
                homeTeam = "Miami Heat",
                awayTeam = "Milwaukee Bucks",
                homeProbability = 41,
                awayProbability = 59,
                homeForm = "LWLLW",
                awayForm = "WWWWW",
                arena = "Kaseya Center",
                city = "Miami",
                matchTime = "21:00",
                matchDate = "07 Aug 2026"
            ),

            Game(
                homeTeam = "Dallas Mavericks",
                awayTeam = "Denver Nuggets",
                homeProbability = 51,
                awayProbability = 49,
                homeForm = "WLWWW",
                awayForm = "LWWWW",
                arena = "American Airlines Center",
                city = "Dallas",
                matchTime = "19:30",
                matchDate = "07 Aug 2026"
            )

        )
    }
}