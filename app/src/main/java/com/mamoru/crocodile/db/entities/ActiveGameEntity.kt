package com.mamoru.crocodile.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "active_games")
data class ActiveGameEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val selectedDictionaryId: Int = -1,
    val completedWords: Int = 0,
)