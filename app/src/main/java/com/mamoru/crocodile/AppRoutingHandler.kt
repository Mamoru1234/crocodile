package com.mamoru.crocodile

import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder

class AppRoutingHandler(private val navController: NavController) {
    fun onGameStarted() = navController.navigate(SelectDictionaryScreenRoute) {
        popUpTo(SelectDictionaryScreenRoute) {
            inclusive = true
        }
    }

    fun onDictionarySelected() = navController.navigate(
        NextWordScreenRoute
    ) {
        popUpTo(NextWordScreenRoute) {
            inclusive = true
        }
    }

    fun onSettings() = navController.navigate(SettingsScreenRoute)

    fun onBackClick() = navController.navigateUp()
}

fun NavOptionsBuilder.popUpToTop(navController: NavController) {
    popUpTo(navController.currentBackStackEntry?.destination?.route ?: return) {
        inclusive =  true
    }
}