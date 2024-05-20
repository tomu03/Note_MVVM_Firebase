package com.example.noteview

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.noteview.Adapter.NoteAdapter
import com.example.noteview.Model.Note
import com.example.noteview.ViewModel.NoteViewModel
import com.example.noteview.databinding.ActivityMainBinding
import java.util.UUID


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: NoteViewModel by viewModels()
    private val noteAdapter by lazy{
        NoteAdapter(mutableListOf(),::onEditNote, ::onDeleteNote,)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        binding.addBtn.setOnClickListener {
            val note = Note(
                id = UUID.randomUUID().toString(),
                title = binding.titleEt.text.toString(),
                content = binding.contentEt.text.toString()
            )
            viewModel.addNote(note).observe(this, Observer{ result->
                result.onSuccess{
                    clearInput()
                    fetchNotes()
                }.onFailure{

                }
            })
        }
        fetchNotes()
    }

    private fun fetchNotes(){
        viewModel.getNote().observe(this, Observer{
            it.onSuccess {
                noteAdapter.updateNotes(it)
            }.onFailure {

            }
        })
    }

    private fun clearInput() {
        binding.titleEt.text!!.clear()
        binding.contentEt.text!!.clear()

    }

    private fun setupRecyclerView() {
        binding.rvNote.layoutManager = LinearLayoutManager(this)
        binding.rvNote.adapter = noteAdapter
    }

    fun onDeleteNote(s: String) {
        viewModel.deleteNote(s).observe(this, Observer{
            it.onSuccess {
                fetchNotes()
            }.onFailure {  }
        })
    }

    fun onEditNote(note: Note) {
        binding.titleEt.setText(note.title)
        binding.contentEt.setText(note.content)

        binding.addBtn.setOnClickListener {
          note.title = binding.titleEt.text.toString()
          note.content = binding.contentEt.text.toString()
            viewModel.updateNote(note).observe(this, Observer{ result ->
                result.onSuccess {
                    clearInput()
                    fetchNotes()
                    resetAddButton()
                }.onFailure {

                }
            })
        }
    }

    private fun resetAddButton() {
        binding.addBtn.setOnClickListener {
            val note = Note(
                id = UUID.randomUUID().toString(),
                title = binding.titleEt.text.toString(),
                content = binding.contentEt.text.toString()
            )
            viewModel.addNote(note).observe(this, Observer{
                it.onSuccess {
                    clearInput()
                    fetchNotes()
                }.onFailure {  }
            })
        }
    }
}