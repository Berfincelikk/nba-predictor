package com.example.nba.presentation.navigation

sealed class Screen(val route: String) {

    object Splash : Screen("splash")

    object Home : Screen("home")

    object Result : Screen("result/{gameId}") {
        fun createRoute(gameId: String): String {
            return "result/$gameId"
        }
    }
}