package com.mamoru.crocodile.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mamoru.crocodile.db.entities.UsedWordEntity

@Dao
interface UsedWordDao {
    @Query("SELECT * FROM used_word")
    suspend fun getAll(): List<UsedWordEntity>

    @Insert
    suspend fun insert(usedWordEntity: UsedWordEntity)
}