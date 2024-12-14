package com.speedymovil.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.speedymovil.ui.DetailScreen
import com.speedymovil.ui.theme.HomeScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("detail/{id}",
            arguments = listOf(
                navArgument(
                    name = "id"
                ) {
                    type = NavType.IntType
                }
            )
        ) {id->
            id.arguments?.getInt("id")?.let { idX ->
                DetailScreen(
                    id = idX,
                    navController = navController
                )
            }
        }
    }
}