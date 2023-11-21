package com.example.roomtugas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import com.example.roomtugas.database.Note
import com.example.roomtugas.database.NoteDao
import com.example.roomtugas.database.NoteRoomDatabase
import com.example.roomtugas.databinding.ActivityMainBinding
import com.example.roomtugas.databinding.UpdateItemBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class updateItem : AppCompatActivity() {

    private lateinit var binding: UpdateItemBinding

    private var updateId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = UpdateItemBinding.inflate(layoutInflater)
        setContentView(binding.root)

        updateId = intent.getIntExtra("itemId",0)

        setEmptyField()

        binding.editButton.setOnClickListener {
            val titlee = binding.editTitle.text.toString()
            val descc = binding.editDesc.text.toString()
            val datee = binding.editDate.text.toString()
            update(Note(id = updateId, title = titlee, description = descc, date = datee))
            Toast.makeText(this,updateId.toString(),Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.deleteButton.setOnClickListener{
            val title = binding.editTitle.text.toString()
            val desc = binding.editDesc.text.toString()
            val date = binding.editDate.text.toString()
            delete(Note(id = updateId, title = title, description = desc,date = date))
            finish()
        }
    }

    private fun update(note: Note) {
        MainActivity.executorService.execute { MainActivity.mNotesDao.update(note) }
    }

    private fun delete(note: Note){
        MainActivity.executorService.execute { MainActivity.mNotesDao.delete(note) }
    }

    private fun setEmptyField() {
        with(binding) {
            editTitle.setText(intent.getStringExtra("itemTitle"))
            editDesc.setText(intent.getStringExtra("itemDesc"))
            editDate.setText(intent.getStringExtra("itemDate"))
        }
    }
}