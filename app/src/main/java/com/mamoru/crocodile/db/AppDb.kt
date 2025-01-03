package com.mamoru.crocodile.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mamoru.crocodile.db.dao.ActiveGameDao
import com.mamoru.crocodile.db.dao.UsedWordDao
import com.mamoru.crocodile.db.dao.WordDao
import com.mamoru.crocodile.db.dao.WordDictionaryDao
import com.mamoru.crocodile.db.entities.ActiveGameEntity
import com.mamoru.crocodile.db.entities.UsedWordEntity
import com.mamoru.crocodile.db.entities.WordDictionaryEntity
import com.mamoru.crocodile.db.entities.WordEntity

@Database(entities = [
    ActiveGameEntity::class, WordDictionaryEntity::class, WordEntity::class, UsedWordEntity::class
 ], version = 4)
abstract class AppDb: RoomDatabase() {
    abstract fun activeGameDao(): ActiveGameDao
    abstract fun dictionaryDao(): WordDictionaryDao
    abstract fun wordDao(): WordDao
    abstract fun usedWordDao(): UsedWordDao
}