package com.mamoru.crocodile.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "words",
    foreignKeys = [
        ForeignKey(
            entity = WordDictionaryEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("dictionaryId"),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["word", "dictionaryId"], unique = true)]
)
data class WordEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val word: String,
    @ColumnInfo(index = true)
    val dictionaryId: Int,
)