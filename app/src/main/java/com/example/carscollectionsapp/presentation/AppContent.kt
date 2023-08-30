package com.example.carscollectionsapp.presentation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.carscollectionsapp.presentation.main_screen.MainScreen
import com.example.carscollectionsapp.presentation.navigation.CarsAppScreens
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppContent() {
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = CarsAppScreens.CarsListScreen.route,
    ) {
        composable(route = CarsAppScreens.CarsListScreen.route) {
            MainScreen(navController = navController)
        }

        /*composable(
            route = CarsAppScreens.CarAddingScreen.route,
            arguments = listOf(
                navArgument(CarsAppScreens.carIdArgument) {
                    type = NavType.LongType
                    defaultValue = -1L
                }
            )
        ) {
            CarAddingScreen(
                cocktailId = it.arguments?.getLong(CarsAppScreens.carIdArgument)!!,
                navController = navController
            )
        }

        composable(
            route = CarsAppScreens.CarDetailsScreen.route,
            arguments = listOf(
                navArgument(CarsAppScreens.carIdArgument) {
                    type = NavType.LongType
                }
            )
        ) {
            CarDetailsScreen(
                cocktailId = it.arguments?.getLong(CarsAppScreens.carIdArgument)!!,
                navController = navController
            )
        }*/
    }
}