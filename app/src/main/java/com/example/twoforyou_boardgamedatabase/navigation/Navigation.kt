package com.example.twoforyou_boardgamedatabase.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.twoforyou_boardgamedatabase.ui.display.DisplayBoardgameScreen


@Composable
fun Navigation(
    navController: NavHostController
) {

    NavHost(
        navController = navController,
        startDestination = Screen.DisplayScreen
    ) {
        composable<Screen.DisplayScreen> {
            DisplayBoardgameScreen(
                navController = navController
            )
        }
    }
}
