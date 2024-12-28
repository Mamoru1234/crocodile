package com.mamoru.crocodile.screen.game.next_word_screen

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mamoru.crocodile.R
import com.mamoru.crocodile.components.AppTopBar

@ExperimentalMaterial3Api
@Composable
fun NextWordScreen(onBackClick: () -> Unit, model: NextWordScreenModel = hiltViewModel()) {
    val nextWord by model.selectedWord.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            AppTopBar(onBackClick) { Text(stringResource(R.string.next_word_label)) }
        },
    ) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            Column(modifier = Modifier.padding(horizontal = 8.dp).fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                WordComponent(nextWord)
                Button(model::selectNextWord, modifier = Modifier.padding(vertical = 12.dp), enabled = nextWord != null) {
                    Text(stringResource(R.string.next_word))
                }
                WordLink(nextWord)
            }
        }
    }
}

@Composable
fun WordLink(word: String?) {
    val uriHandler = LocalUriHandler.current
    val googleLink = remember(word) {
        return@remember "https://www.google.com/search?q=${Uri.encode(word)}"
    }
    if (word == null) {
        return
    }
    OutlinedButton({
        uriHandler.openUri(googleLink)
    }, modifier = Modifier.padding(vertical = 12.dp)) {
        Text(stringResource(R.string.next_word_google), style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun WordComponent(word: String?) {
    if (word == null) {
        Text(
            stringResource(R.string.next_word_empty_dictionary),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary,
            fontSize = 34.sp)
    } else {
        Text(word, fontWeight = FontWeight.Bold, fontSize = 40.sp)
    }
}
