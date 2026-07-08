package com.example.notes.database

import android.content.Context
import androidx.room.Room


object DatabaseProvider {
    private var database: NoteDatabase? = null
    fun getDatabase(context: Context): NoteDatabase{
        if (database ==null){
            database = Room.databaseBuilder(
                context.applicationContext,
                NoteDatabase::class.java,
                "notes_database"
            ).build()
        }
        return database!!
    }
}
