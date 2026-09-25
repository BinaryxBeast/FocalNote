package com.oneline.focalnote.data

data class NoteUiState(
    val text: String = "",
    val category: String = "",
    val isStarred: Boolean = false,
    val isPinned: Boolean = false,
    val fontType: FontType = FontType.DEFAULT
)

