package com.mamoru.crocodile.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "used_word", foreignKeys = [
    ForeignKey(
        entity = ActiveGameEntity::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("gameId"),
        onDelete = ForeignKey.CASCADE,
    ),
])
data class UsedWordEntity(
    @PrimaryKey val id: Int,
    @ColumnInfo(index = true)
    val gameId: Int,
)