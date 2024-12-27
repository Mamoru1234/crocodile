package com.mamoru.crocodile.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.mamoru.crocodile.db.entities.WordEntity

@Dao
interface WordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertMany(words: List<WordEntity>)

    @Query("SELECT * FROM words WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): WordEntity?

    @Query("SELECT count(*) FROM words WHERE dictionaryId = :dictionaryId")
    suspend fun countByDictionary(dictionaryId: Int): Int

    @Query("SELECT id FROM words WHERE dictionaryId = :dictionaryId AND id NOT IN (:usedIds)")
    suspend fun getNotUsedIds(usedIds: List<Int>, dictionaryId: Int): List<Int>
}