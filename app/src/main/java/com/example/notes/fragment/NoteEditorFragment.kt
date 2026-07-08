package com.example.notes.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.notes.factory.ViewModelFactory
import com.example.notes.database.DatabaseProvider
import com.example.notes.databinding.FragmentNoteEditorBinding
import com.example.notes.entity.Note
import com.example.notes.repository.NoteRepository
import com.example.notes.viewModel.NoteEditorViewModel
import kotlinx.coroutines.launch

class NoteEditorFragment : Fragment() {

    private var _binding: FragmentNoteEditorBinding? = null
    private val binding get() = _binding!!

    private val repository by lazy {
        val database = DatabaseProvider.getDatabase(requireContext())
        val dao = database.noteDao()
        NoteRepository(dao)
    }

    private val viewModel: NoteEditorViewModel by viewModels {
        ViewModelFactory(repository)
    }

    private val args: NoteEditorFragmentArgs by navArgs()

    private val noteId by lazy {
        args.noteId
    }

    private var originalNote: Note? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNoteEditorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.toolbar.setNavigationOnClickListener {
            saveAndNavigateUp()
        }

        setupBackPressHandler()

        if (noteId != -1) {
            observeNote()
            viewModel.getNoteById(noteId)
        }
    }

    private fun observeNote() {
        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){

                viewModel.noteById.collect { note ->
                    note?.let {
                        // Save original note for comparison
                        originalNote = it
                        binding.editTextTitle.setText(it.title)
                        binding.editTextBody.setText(it.description)
                    }
                }
                }
            }
        }
    private fun setupBackPressHandler() {

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                saveAndNavigateUp()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            callback
        )
    }

    private fun saveAndNavigateUp() {

        val title = binding.editTextTitle.text?.toString()?.trim().orEmpty()
        val body = binding.editTextBody.text?.toString()?.trim().orEmpty()

        if (noteId == -1) {

            if (title.isNotEmpty() || body.isNotEmpty()) {
                viewModel.addNote(
                    Note(
                        title = title,
                        description = body
                    )
                )
            }

        } else {

            val currentNote = Note(
                id = noteId,
                title = title,
                description = body
            )

            if (currentNote != originalNote) {
                viewModel.updateNote(currentNote)
            }
        }

        findNavController().popBackStack()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
