package com.oneline.focalnote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oneline.focalnote.data.NoteEntity
import com.oneline.focalnote.data.NoteUiState
import com.oneline.focalnote.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NoteViewModel(
    private val repository: NoteRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(NoteUiState())
    val uiState: StateFlow<NoteUiState> = _uiState

    fun updateText(text: String){
        _uiState.update {
            it.copy(text = text)
        }
    }

    fun updateCategory(category: String){
        _uiState.update {
            it.copy(category = category)
        }
    }

    fun toggleStar(){
        _uiState.update {
            it.copy(isStarred = !it.isStarred)
        }
    }

    fun togglePin(){
        _uiState.update {
            it.copy(isPinned = !it.isPinned)
        }
    }

    fun saveNote(){
        viewModelScope.launch {
            val state = _uiState.value

            val note = NoteEntity(
                text = state.text,
                category = state.category,
                isStarred = state.isPinned,
                isPinned = state.isPinned
            )

            repository.insertNote(note)
        }
    }
}