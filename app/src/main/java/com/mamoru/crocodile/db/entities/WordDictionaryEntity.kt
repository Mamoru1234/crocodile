package com.mamoru.crocodile.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "word_dictionaries",
    indices = [Index(value = ["name"], unique = true)]
)
data class WordDictionaryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
)