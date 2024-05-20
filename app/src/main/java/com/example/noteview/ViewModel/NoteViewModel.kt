package com.example.noteview.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.noteview.Model.Note
import com.example.noteview.repository.NoteRepository
import kotlinx.coroutines.Dispatchers

class NoteViewModel: ViewModel() {
    private val repository = NoteRepository()

    fun addNote(note: Note) = liveData(Dispatchers.IO){
        try {
            repository.addNote(note)
            emit(Result.success(true))
        }catch (e:Exception){
            emit(Result.failure<Boolean>(e))
        }
    }

    fun getNote() = liveData(Dispatchers.IO){
        try {
            val notes = repository.getNote()
            emit(Result.success(notes))
        }catch (e:Exception){
            emit(Result.failure<List<Note>>(e))
        }
    }

    fun updateNote(note:Note) = liveData(Dispatchers.IO){
        try {
            repository.updateNote(note)
            emit(Result.success(true))
        }catch (e:Exception){
            emit(Result.failure<Boolean>(e))
        }
    }

    fun deleteNote(noteId:String) = liveData(Dispatchers.IO){
        try {
            repository.deleteNote(noteId)
            emit(Result.success(true))
        }catch (e:Exception){
            emit(Result.failure<Boolean>(e))
        }
    }
}