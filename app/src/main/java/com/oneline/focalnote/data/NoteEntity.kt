package com.oneline.focalnote.data

import androidx.compose.ui.text.font.FontFamily
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val text: String,
    val category: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isStarred: Boolean,
    val isPinned: Boolean,
    val fontType: String = "DEFAULT"
) {
}