package com.mamoru.crocodile.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.mamoru.crocodile.db.entities.WordDictionaryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDictionaryDao {
    @Insert
    suspend fun insert(dictionary: WordDictionaryEntity): Long

    @Query("SELECT * FROM word_dictionaries")
    fun listAll(): Flow<List<WordDictionaryEntity>>

    @Query("SELECT * FROM word_dictionaries WHERE name = :name LIMIT 1")
    suspend fun findByName(name: String): WordDictionaryEntity?

    @Delete
    suspend fun deleteDictionary(dictionary: WordDictionaryEntity)
}