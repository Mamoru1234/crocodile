package com.mamoru.crocodile.screen.game.select_dictionary_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mamoru.crocodile.db.dao.ActiveGameDao
import com.mamoru.crocodile.db.dao.WordDictionaryDao
import com.mamoru.crocodile.db.entities.WordDictionaryEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SelectDictionaryScreenModel @Inject constructor(
    private val activeGameDao: ActiveGameDao,
    private val wordDictionaryDao: WordDictionaryDao,
): ViewModel() {
    companion object {
        const val TAG = "SelectDictionaryScreenModel"
    }
    val allDictionaries: StateFlow<List<WordDictionaryEntity>> = wordDictionaryDao.listAll().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(1000 * 60 * 30),
        initialValue = emptyList(),
    )

    fun selectDictionary(dictionaryId: Int, onDictionarySelected: () -> Unit) {
        viewModelScope.launch {
            val activeGame = activeGameDao.getActiveGame()
            if (activeGame == null) {
                Log.v(TAG, "Active game not found")
                return@launch
            }
            activeGameDao.updateGame(activeGame.copy(selectedDictionaryId = dictionaryId))
            onDictionarySelected()
        }
    }
}
