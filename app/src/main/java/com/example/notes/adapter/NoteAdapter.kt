package com.example.notes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.notes.databinding.ItemNoteBinding
import com.example.notes.entity.Note

class NoteAdapter(
    private val onClick: (Note) -> Unit,
    private val onLongClick:(Note)-> Unit
) : ListAdapter<Note, NoteAdapter.NoteViewHolder>(NoteDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NoteViewHolder {

        val binding = ItemNoteBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: NoteViewHolder,
        position: Int
    ) {

        val note = getItem(position)

        holder.binding.apply {

            tvTitle.text = note.title
            tvDescription.text = note.description
            tvLastEdited.text = "feature not implemented"
            root.setOnClickListener {
                onClick(note)
            }
            root.setOnLongClickListener {
                onLongClick(note)
                true
            }
        }
    }

    class NoteViewHolder(
        val binding: ItemNoteBinding
    ) : RecyclerView.ViewHolder(binding.root)

}

class NoteDiffCallback : DiffUtil.ItemCallback<Note>() {

    // Checks whether two objects represent the same item.
    override fun areItemsTheSame(
        oldItem: Note,
        newItem: Note
    ): Boolean {

        return oldItem.id == newItem.id
    }

    // Checks whether item contents are exactly the same.
    override fun areContentsTheSame(
        oldItem: Note,
        newItem: Note
    ): Boolean {

        return oldItem == newItem
    }
}
