package com.example.notes.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.notes.dao.NoteDao
import com.example.notes.entity.Note

@Database(entities = [Note::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun noteDao(): NoteDao
}