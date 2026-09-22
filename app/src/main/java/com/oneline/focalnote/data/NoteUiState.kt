package com.oneline.focalnote.data

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily

data class NoteUiState(
    val text: String = "",
    val fontFamily: String = "Sans",
    val backgroundColor: Long = Color.White.value.toLong()
)
