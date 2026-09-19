package com.oneline.focalnote.repository

import com.oneline.focalnote.data.NoteDao
import com.oneline.focalnote.data.NoteEntity

class NoteRepository(
    private val noteDao: NoteDao
) {
    val allNotes = noteDao.getAllNotes()
    suspend fun insertNote(note: NoteEntity){
        noteDao.insertNote(note)
    }
    suspend fun updateNote(note: NoteEntity){
        noteDao.updateNote(note)
    }

}