package com.mamoru.crocodile.screen.welcome_screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mamoru.crocodile.R

@Composable
fun WelcomeScreen(onGameStarted: () -> Unit, onSettings: () -> Unit, model: WelcomeScreenModel = hiltViewModel()) {
    val hasActiveGame by model.hasActiveGame.collectAsStateWithLifecycle()
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            Column(
                modifier = Modifier.padding(horizontal = 8.dp).fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                if (hasActiveGame) {
                    Button(onGameStarted, modifier = Modifier.padding(bottom = 8.dp)) {
                        Text(stringResource(R.string.welcome_resume_game))
                    }
                }
                Button({ model.startNewGame(onGameStarted) }, modifier = Modifier.padding(bottom = 8.dp)) {
                    Text(stringResource(R.string.welcome_new_game))
                }
                Button(onSettings) {
                    Text(stringResource(R.string.welcome_settings))
                }
            }
        }
    }
}