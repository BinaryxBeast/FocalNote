package com.oneline.focalnote.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily

data class NoteUiState(
    val text: String = "",
    val category: String = "",
    val isStarred: Boolean = false,
    val isPinned: Boolean = false,
)
