package com.oneline.focalnote.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val text: String,
    val category: String,
    val timestamp: Long = System.currentTimeMillis(),
    val isStarred: Boolean,
    val isPinned: Boolean
) {
}