package com.mamoru.crocodile.db

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppDbModule {
    @Singleton
    @Provides
    fun provideYourDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDb::class.java,
        "app_db_v1"
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun activeGameDao(db: AppDb) = db.activeGameDao()

    @Singleton
    @Provides
    fun wordDictionaryDao(db: AppDb) = db.dictionaryDao()

    @Singleton
    @Provides
    fun wordDao(db: AppDb) = db.wordDao()

    @Singleton
    @Provides
    fun usedWordDao(db: AppDb) = db.usedWordDao()
}