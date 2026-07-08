package com.example.notes.repository

import androidx.lifecycle.LiveData
import com.example.notes.dao.NoteDao
import com.example.notes.entity.Note


class NoteRepository(private val noteDao: NoteDao) {

    fun getAllNotes(): LiveData<List<Note>>{
        return noteDao.getAllNotes()
    }
    suspend fun getNoteById(id: Int): Note?{
        return noteDao.getNoteById(id)
    }
    suspend fun insertNote(note: Note){
        noteDao.insertNote(note)
    }
    suspend fun updateNote(note: Note){
        noteDao.updateNote(note)
    }

    suspend fun deleteNote(note: Note){
        noteDao.deleteNote(note)
    }
}
