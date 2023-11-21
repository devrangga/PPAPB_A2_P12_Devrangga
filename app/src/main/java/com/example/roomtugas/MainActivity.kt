package com.example.roomtugas

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomtugas.database.Note
import com.example.roomtugas.database.NoteDao
import com.example.roomtugas.database.NoteRoomDatabase
import com.example.roomtugas.databinding.ActivityMainBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerViewItem: RecyclerView
    private lateinit var itemList: ArrayList<Note>
    private lateinit var itemAdapter: ItemAdapter

    companion object {
        lateinit var mNotesDao: NoteDao
        lateinit var executorService: ExecutorService
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        executorService = Executors.newSingleThreadExecutor()
        val db = NoteRoomDatabase.getDatabase(this)
        mNotesDao = db!!.noteDao()!!

        recyclerViewItem = findViewById(R.id.recyclerPiw)
        recyclerViewItem.setHasFixedSize(true)
        recyclerViewItem.layoutManager = LinearLayoutManager(this)

        itemList = arrayListOf()
        itemAdapter = ItemAdapter(itemList)
        recyclerViewItem.adapter = itemAdapter

        with(binding) {
            fabAdd.setOnClickListener{
                val intent = Intent(this@MainActivity,addItem::class.java)
                startActivity(intent)
            }
        }
    }
    private fun updateRecyclerView() {
        mNotesDao.allNotes.observe(this) { notes ->
            itemList.clear()
            itemList.addAll(notes.map { n ->
                Note(n.id,n.title, n.description, n.date)
            })
            itemAdapter = ItemAdapter(itemList)
            recyclerViewItem.adapter = itemAdapter
        }
    }
    override fun onResume() {
        super.onResume()
        updateRecyclerView()
    }
}
