package com.example.nba.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.NavType
import com.example.nba.presentation.screens.HomeScreen
import com.example.nba.presentation.screens.ResultScreen
import com.example.nba.presentation.screens.SplashScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {

        composable(Screen.Splash.route) {
            SplashScreen(
                navController = navController
            )
        }

        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController
            )
        }

        composable(
            route = Screen.Result.route,
            arguments = listOf(
                navArgument("gameId") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val gameId = backStackEntry.arguments?.getString("gameId") ?: ""

            ResultScreen(
                navController = navController,
                gameId = gameId
            )
        }
    }
}