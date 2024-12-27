package com.mamoru.crocodile.screen.settings_screen

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mamoru.crocodile.db.dao.ActiveGameDao
import com.mamoru.crocodile.db.dao.WordDao
import com.mamoru.crocodile.db.dao.WordDictionaryDao
import com.mamoru.crocodile.db.entities.WordDictionaryEntity
import com.mamoru.crocodile.db.entities.WordEntity
import com.mamoru.crocodile.screen.welcome_screen.SystemDictionary
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.stream.Collectors
import javax.inject.Inject

@HiltViewModel
class SettingsScreenModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val activeGameDao: ActiveGameDao,
    private val wordDictionaryDao: WordDictionaryDao,
    private val wordDao: WordDao,
): ViewModel() {
    private val _isDictionariesLoading = MutableStateFlow(false)
    val isDictionariesLoading: StateFlow<Boolean> get() = _isDictionariesLoading.asStateFlow()

    companion object {
        const val TAG = "WelcomeScreenModel"
        val SYSTEM_DICTIONARIES = listOf(
            SystemDictionary("Легкий", "easy"),
            SystemDictionary("Test", "test"),
        )
    }
    fun loadSystemDictionaries() {
        viewModelScope.launch {
            _isDictionariesLoading.value = true
            SYSTEM_DICTIONARIES.forEach {
                loadDictionary(it)
            }
            _isDictionariesLoading.value = false
        }
    }

    fun deleteData() {
        viewModelScope.launch {
            activeGameDao.deleteAll()
            wordDictionaryDao.deleteAll()
        }
    }

    private suspend fun loadDictionary(systemDictionary: SystemDictionary) {
        val dictionary = ensureDictionary(systemDictionary.dictionaryName)
        val filePath = "dictionaries/${systemDictionary.fileName}.txt"
        Log.v(TAG, "Start loading words $filePath")
        val words = withContext(Dispatchers.IO) {
            context.assets.open(filePath).bufferedReader().lines()
                .map(String::trim).filter(String::isNotEmpty)
                .collect(Collectors.toList())
        }
        Log.v(TAG, "Words loaded into memory")
        val wordsInDictionaryBefore = wordDao.countByDictionary(dictionaryId = dictionary.id)
        wordDao.insertMany(words.map { WordEntity(word = it, dictionaryId = dictionary.id) })
        val wordsInDictionaryAfter = wordDao.countByDictionary(dictionaryId = dictionary.id)
        val newWords = wordsInDictionaryAfter - wordsInDictionaryBefore
        Log.v(TAG, "$newWords new words inserted into DB")
    }

    private suspend fun ensureDictionary(dictionaryName: String): WordDictionaryEntity {
        val existingDictionary = wordDictionaryDao.findByName(dictionaryName)
        if (existingDictionary != null) {
            Log.v(TAG, "Dictionary found name = $dictionaryName ${existingDictionary.id}")
            return existingDictionary
        }
        val newDictionaryId = wordDictionaryDao.insert(WordDictionaryEntity(name = dictionaryName))
        Log.v(TAG, "Dictionary created name = $dictionaryName $newDictionaryId")
        return wordDictionaryDao.findByName(dictionaryName)!!
    }
}