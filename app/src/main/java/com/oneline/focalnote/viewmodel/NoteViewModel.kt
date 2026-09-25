package com.oneline.focalnote.viewmodel

import androidx.compose.ui.text.font.FontFamily
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oneline.focalnote.data.FontType
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
    fun updateFont(){
        _uiState.update {
            it.copy(fontType= it.fontType.next())
        }
    }

    fun saveNote(){
        viewModelScope.launch {
            val state = _uiState.value

            val note = NoteEntity(
                text = state.text,
                category = state.category,
                isStarred = state.isStarred,
                isPinned = state.isPinned,
                fontType = "DEFAULT"
            )

            repository.insertNote(note)
        }


    }
}