package com.mamoru.crocodile.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.mamoru.crocodile.db.entities.ActiveGameEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ActiveGameDao {
    @Query("SELECT * FROM active_games LIMIT 1")
    suspend fun getActiveGame(): ActiveGameEntity?

    @Query("SELECT * FROM active_games LIMIT 1")
    fun getActiveGameFlow(): Flow<ActiveGameEntity?>

    @Insert
    suspend fun insertGame(gameEntity: ActiveGameEntity): Long

    @Update
    suspend fun updateGame(gameEntity: ActiveGameEntity)

    @Query("DELETE FROM active_games")
    suspend fun deleteAll()
}