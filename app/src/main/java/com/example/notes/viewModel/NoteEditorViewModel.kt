package com.example.notes.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.entity.Note
import com.example.notes.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteEditorViewModel @Inject constructor(private val repository: NoteRepository) : ViewModel() {


    private val _noteById = MutableStateFlow<Note?>(null)
    val noteById: StateFlow<Note?> = _noteById


    fun addNote(note: Note) {
        viewModelScope.launch {
            repository.insertNote(note)
        }
    }

    fun getNoteById(id: Int) {
        viewModelScope.launch {
            _noteById.value = repository.getNoteById(id)
        }
    }

    fun updateNote(note: Note){
        viewModelScope.launch {
            repository.updateNote(note)
        }
    }

}

