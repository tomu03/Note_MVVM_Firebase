package com.example.noteview.repository

import com.example.noteview.Model.Note
import com.example.noteview.utils.Constant.NOTES

class NoteRepository {
    private val database = FirebaseDatabase.getInstance()
    private val noteRef = database.getReference(NOTES)

    suspend fun addNote(note: Note){
        noteRef.child(note.id).setValue(note).await()
    }

    suspend fun getNote():List<Note>{
        val snapshot = noteRef.get().await()
        return snapshot.children.mapNotNull { it.getValue(Note::class.java)}
    }

    suspend fun updateNote(note: Note){
        noteRef.child(note.id).setValue(note).await()
    }

    suspend fun deleteNote(noteId:String){
        noteRef.child(noteId).removeValue().await()
    }
}