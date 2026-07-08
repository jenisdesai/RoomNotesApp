package com.example.notes.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.notes.R
import com.example.notes.ViewModelFactory
import com.example.notes.adapter.NoteAdapter
import com.example.notes.database.DatabaseProvider
import com.example.notes.databinding.DialogDeleteNoteBinding
import com.example.notes.databinding.FragmentNotesListBinding
import com.example.notes.entity.Note
import com.example.notes.repository.NoteRepository
import com.example.notes.viewModel.NotesListViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class NotesListFragment : Fragment() {

    private var _binding: FragmentNotesListBinding? = null
    private val binding get() = _binding!!
    private val repository by lazy {
        val databse = DatabaseProvider.getDatabase(requireContext())
        val dao = databse.noteDao()
        NoteRepository(dao)
    }

    private val adapter by lazy {
        NoteAdapter(onClick = { note ->
            val action =
                NotesListFragmentDirections.actionNotesListFragmentToNoteEditorFragment(
                    noteId = note.id
                )
            findNavController().navigate(action)
        }, onLongClick = { note ->
            showDeleteDialog(note)
        }
        )
    }

    private val viewModel: NotesListViewModel by viewModels {
        ViewModelFactory(repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerNotes.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.recyclerNotes.adapter = adapter
        viewModel.notes.observe(viewLifecycleOwner){ notes->
            adapter.submitList(notes)
        }

        binding.fabAddNote.setOnClickListener{
            val action = NotesListFragmentDirections.actionNotesListFragmentToNoteEditorFragment()
            findNavController().navigate(action)
        }
    }

    private fun showDeleteDialog(note: Note) {
        val dialogBinding = DialogDeleteNoteBinding.inflate(layoutInflater,null,false)
        val dialog = MaterialAlertDialogBuilder(requireContext(), R.style.CustomAlertDialog)
            .setView(dialogBinding.root)
            .create()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        dialogBinding.tvNoteTitlePreview.text = "\"${note.title}\""

        dialogBinding.btnCancel.setOnClickListener {
            dialog.dismiss()
        }

        dialogBinding.btnDeleteConfirm.setOnClickListener {
            viewModel.deleteNote(note = note)
            dialog.dismiss()
        }

        dialog.show()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
