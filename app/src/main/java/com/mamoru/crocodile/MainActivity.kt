package com.mamoru.crocodile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.mamoru.crocodile.screen.game.next_word_screen.NextWordScreen
import com.mamoru.crocodile.screen.game.select_dictionary_screen.SelectDictionaryScreen
import com.mamoru.crocodile.screen.settings_screen.SettingsScreen
import com.mamoru.crocodile.screen.welcome_screen.WelcomeScreen
import com.mamoru.crocodile.ui.theme.CrocodileTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CrocodileTheme {
                val navController = rememberNavController()
                val appRoutingHandler = AppRoutingHandler(navController)
                NavHost(navController, startDestination = WelcomeScreenRoute) {
                    composable<WelcomeScreenRoute> {
                        WelcomeScreen(onGameStarted = appRoutingHandler::onGameStarted, onSettings = appRoutingHandler::onSettings) }
                    composable<SelectDictionaryScreenRoute> {
                        SelectDictionaryScreen(
                            onDictionarySelected = appRoutingHandler::onDictionarySelected,
                            onBackClick = appRoutingHandler::onBackClick
                        )
                    }
                    composable<NextWordScreenRoute> {
                        NextWordScreen(onBackClick = appRoutingHandler::onBackClick)
                    }
                    composable<SettingsScreenRoute> { SettingsScreen(onBackClick = appRoutingHandler::onBackClick) }
                }
            }
        }
    }
}
