package com.example.notes.viewModel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.notes.entity.Note
import com.example.notes.repository.NoteRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class NotesListViewModel @Inject constructor (private val repository: NoteRepository) : ViewModel() {
    private val _notes = MutableStateFlow<List<Note>>(emptyList())
    val notes: StateFlow<List<Note>> = _notes


    init {
        viewModelScope.launch {
            repository.getAllNotes().collect { notesList ->
                _notes.value = notesList
            }
        }
    }
    fun deleteNote(note: Note){
        viewModelScope.launch {
            repository.deleteNote(note)
        }
    }
}
