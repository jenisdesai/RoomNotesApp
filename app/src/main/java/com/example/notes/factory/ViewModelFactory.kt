package com.example.notes.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.notes.repository.NoteRepository
import com.example.notes.viewModel.NoteEditorViewModel
import com.example.notes.viewModel.NotesListViewModel

class ViewModelFactory(private val repository: NoteRepository) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {

            modelClass.isAssignableFrom(NoteEditorViewModel::class.java)->{
                NoteEditorViewModel(repository) as T
            }
            modelClass.isAssignableFrom(NotesListViewModel::class.java)->{
                NotesListViewModel(repository) as T
            }
            else->{
                throw IllegalArgumentException("Unknown viewmodel class")
            }
        }
    }
//    companion object{
//        fun provideFactory(repository: NoteRepository): ViewModelProvider.Factory =
//            viewModelFactory {
//                initializer { NoteEditorViewModel(repository) }
//            }
//    }
}