package com.oneline.focalnote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oneline.focalnote.data.NoteEntity
import com.oneline.focalnote.repository.NoteRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class NoteViewModel(
    private val repository: NoteRepository
) : ViewModel() {

    // All notes from the database
    val notes: StateFlow<List<NoteEntity>> = repository.allNotes
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addNote(
        text: String,
        category: String
    ) {
        viewModelScope.launch {
            val note = NoteEntity(
                id = 0,
                text = text,
                category = category,
                isStarred = false,
                isPinned = false
            )
            repository.insertNote(note)
        }
    }

    fun updateNote(note: NoteEntity) {
        viewModelScope.launch {
            repository.updateNote(note)
        }
    }

    fun toggleStarred(note: NoteEntity) {
        viewModelScope.launch {
            val updatedNote = note.copy(
                isStarred = !note.isStarred,
                timestamp = System.currentTimeMillis()
            )
            repository.updateNote(updatedNote)
        }
    }
}