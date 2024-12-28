package com.mamoru.crocodile.screen.welcome_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mamoru.crocodile.db.dao.ActiveGameDao
import com.mamoru.crocodile.db.entities.ActiveGameEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

data class SystemDictionary(
    val dictionaryName: String,
    val fileName: String,
)

@HiltViewModel
class WelcomeScreenModel @Inject constructor(
    private val activeGameDao: ActiveGameDao,
): ViewModel() {
    companion object {
        const val TAG = "WelcomeScreenModel"
    }

    val hasActiveGame: StateFlow<Boolean> = activeGameDao.getActiveGameFlow().map { it != null }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1000 * 60 * 30),
        initialValue = false,
    )

    fun startNewGame(onGameStarted: () -> Unit) {
        viewModelScope.launch {
            Log.v(TAG, "Starting new game")
            activeGameDao.deleteAll()
            activeGameDao.insertGame(ActiveGameEntity())
            Log.v(TAG, "New game started")
            onGameStarted()
        }
    }
}