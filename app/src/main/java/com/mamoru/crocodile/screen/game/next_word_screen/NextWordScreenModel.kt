package com.mamoru.crocodile.screen.game.next_word_screen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mamoru.crocodile.db.dao.ActiveGameDao
import com.mamoru.crocodile.db.dao.UsedWordDao
import com.mamoru.crocodile.db.dao.WordDao
import com.mamoru.crocodile.db.entities.UsedWordEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class NextWordScreenModel @Inject constructor(
    private val wordDao: WordDao,
    private val activeGameDao: ActiveGameDao,
    private val usedWordDao: UsedWordDao,
): ViewModel() {
    private val _selectedWord = MutableStateFlow<String?>(null)
    val selectedWord: StateFlow<String?> get() = _selectedWord.asStateFlow()

    companion object {
        const val TAG = "NextWordScreenModel"
    }

    init {
        selectNextWord()
    }

    fun selectNextWord() {
        viewModelScope.launch {
            val activeGame = activeGameDao.getActiveGame()
            if (activeGame == null) {
                Log.v(TAG, "Active game not found")
                return@launch
            }
            if (activeGame.selectedDictionaryId < 0) {
                Log.v(TAG, "Active game dictionary not selected")
                return@launch
            }
            val usedWords = usedWordDao.getAll().map(UsedWordEntity::id)
            val notUsed = wordDao.getNotUsedIds(usedWords, activeGame.selectedDictionaryId)
            Log.v(TAG, "Not used words ${notUsed.size}")
            if (notUsed.isEmpty()) {
                Log.i(TAG, "No unused words left")
                _selectedWord.value = null
                return@launch
            }
            val nextIndex = Random.nextInt(notUsed.size)
            val nextWordId = notUsed[nextIndex]
            val nextWord = wordDao.getById(nextWordId)
            if (nextWord == null) {
                Log.v(TAG, "No word selected")
                _selectedWord.value = null
                return@launch
            }
            _selectedWord.value = nextWord.word
            usedWordDao.insert(UsedWordEntity(id = nextWordId, gameId = activeGame.id))
        }
    }
}