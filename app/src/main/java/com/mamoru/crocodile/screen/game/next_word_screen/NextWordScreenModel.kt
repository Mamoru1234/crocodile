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
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class NextWordScreenModel @Inject constructor(
    private val wordDao: WordDao,
    private val activeGameDao: ActiveGameDao,
    private val usedWordDao: UsedWordDao,
): ViewModel() {
    val completedWords: StateFlow<Int?> = activeGameDao.getActiveGameFlow()
        .map { it?.completedWords }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(1000 * 60 * 30),
            initialValue = null,
        )

    private val _selectedWord = MutableStateFlow<String?>(null)
    val selectedWord: StateFlow<String?> get() = _selectedWord.asStateFlow()

    companion object {
        const val TAG = "NextWordScreenModel"
    }

    init {
        selectNextWord()
    }

    fun completedWord() {
        viewModelScope.launch {
            val activeGameEntity = activeGameDao.getActiveGame()!!
            activeGameDao.updateGame(activeGameEntity.copy(completedWords = activeGameEntity.completedWords + 1))
            chooseNextWord()
        }
    }

    fun resetCompletedWords() {
        viewModelScope.launch {
            val activeGameEntity = activeGameDao.getActiveGame()!!
            activeGameDao.updateGame(activeGameEntity.copy(completedWords = 0))
        }
    }

    fun selectNextWord() {
        viewModelScope.launch {
            chooseNextWord()
        }
    }

    private suspend fun chooseNextWord() {
        val activeGame = activeGameDao.getActiveGame()
        if (activeGame == null) {
            Log.v(TAG, "Active game not found")
            return
        }
        if (activeGame.selectedDictionaryId < 0) {
            Log.v(TAG, "Active game dictionary not selected")
            return
        }
        val usedWords = usedWordDao.getAll().map(UsedWordEntity::id)
        val notUsed = wordDao.getNotUsedIds(usedWords, activeGame.selectedDictionaryId)
        Log.v(TAG, "Not used words ${notUsed.size}")
        if (notUsed.isEmpty()) {
            Log.i(TAG, "No unused words left")
            _selectedWord.value = null
            return
        }
        val nextIndex = Random.nextInt(notUsed.size)
        val nextWordId = notUsed[nextIndex]
        val nextWord = wordDao.getById(nextWordId)
        if (nextWord == null) {
            Log.v(TAG, "No word selected")
            _selectedWord.value = null
            return
        }
        _selectedWord.value = nextWord.word
        usedWordDao.insert(UsedWordEntity(id = nextWordId, gameId = activeGame.id))
    }
}