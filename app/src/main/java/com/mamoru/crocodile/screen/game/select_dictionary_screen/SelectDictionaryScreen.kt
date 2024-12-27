package com.mamoru.crocodile.screen.game.select_dictionary_screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.mamoru.crocodile.components.AppTopBar
import com.mamoru.crocodile.db.entities.WordDictionaryEntity

@ExperimentalMaterial3Api
@Composable
fun SelectDictionaryScreen(
    onDictionarySelected: () -> Unit,
    onBackClick: () -> Unit,
    model: SelectDictionaryScreenModel = hiltViewModel()
) {
    val dictionaries by model.allDictionaries.collectAsStateWithLifecycle()
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {AppTopBar(onBackClick) { Text(stringResource(R.string.select_dictionary_label)) }}) { innerPadding ->
        Surface(modifier = Modifier.padding(innerPadding)) {
            LazyColumn(modifier = Modifier.padding(horizontal = 8.dp).fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                items(dictionaries, key = WordDictionaryEntity::id) {
                    Button({
                        model.selectDictionary(it.id, onDictionarySelected)
                    }) {
                        Text(it.name)
                    }
                }
            }
        }
    }
}
