package com.example.notes.di

import android.content.Context
import androidx.room.Room
import com.example.notes.dao.NoteDao
import com.example.notes.database.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NotePadModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): NoteDatabase {
            return Room.databaseBuilder(
                context,
                NoteDatabase::class.java,
                "note_database"
            ).build()
    }

    @Provides
    fun provideDao(database: NoteDatabase): NoteDao {
       return database.noteDao()
    }
}

