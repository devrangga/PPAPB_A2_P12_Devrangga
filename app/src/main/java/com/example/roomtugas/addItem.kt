package com.example.roomtugas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.roomtugas.database.Note
import com.example.roomtugas.database.NoteDao
import com.example.roomtugas.database.NoteRoomDatabase
import com.example.roomtugas.databinding.AddItemBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class addItem : AppCompatActivity() {
    private lateinit var binding: AddItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.addButton.setOnClickListener {
            val title = binding.addTitle.text.toString()
            val desc = binding.addDesc.text.toString()
            val date = binding.addDate.text.toString()

            insert(Note(title = title, description = desc, date = date))
            finish()
        }
    }

    private fun insert(note: Note) {
        MainActivity.executorService.execute { MainActivity.mNotesDao.insert(note) }
    }
}

