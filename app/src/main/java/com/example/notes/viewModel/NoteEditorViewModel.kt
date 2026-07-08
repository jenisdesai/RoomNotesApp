package com.example.notes.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.entity.Note
import com.example.notes.repository.NoteRepository
import kotlinx.coroutines.launch

class NoteEditorViewModel(private val repository: NoteRepository) : ViewModel() {


    private val _noteData = MutableLiveData<Note?>()
    val noteData: LiveData<Note?>  = _noteData

    fun addNote(note: Note) {
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }

    fun getNoteById(id: Int) {
        viewModelScope.launch {
            _noteData.value = repository.getNoteById(id)
        }
    }

    fun updateNote(note: Note){
        viewModelScope.launch {
            repository.updateNote(note)
        }
    }

}

