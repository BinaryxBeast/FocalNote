package com.oneline.focalnote.viewmodel

import android.graphics.fonts.FontFamily
import com.oneline.focalnote.data.NoteUiState
import com.oneline.focalnote.repository.NoteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class NoteViewModel(
    private val repository: NoteRepository
) {
    private val _uiState = MutableStateFlow(NoteUiState())
    val uiState: StateFlow<NoteUiState> = _uiState

    fun updateText(text: String) {
        _uiState.update {
            it.copy(text = text)
        }
    }

    fun updateFontFamily(fontFamily: String) {
        _uiState.update {
            it.copy(fontFamily = fontFamily)
        }
    }

    fun updateBackgroundColor(color: Long) {
        _uiState.update {
            it.copy(backgroundColor = color)
        }
    }
}