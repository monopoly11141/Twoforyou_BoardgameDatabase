package com.example.twoforyou_boardgamedatabase.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.twoforyou_boardgamedatabase.ui.detail.DetailScreen
import com.example.twoforyou_boardgamedatabase.ui.display.DisplayScreen


@Composable
fun Navigation(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Screen.DetailScreen
    ) {
        composable<Screen.DisplayScreen> {
            DisplayScreen(
                navController = navController
            )
        }
        composable<Screen.DetailScreen> {
            DetailScreen(
                navController = navController
            )
        }
    }
}
