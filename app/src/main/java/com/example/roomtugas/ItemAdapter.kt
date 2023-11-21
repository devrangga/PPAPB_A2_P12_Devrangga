package com.example.roomtugas

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.example.roomtugas.database.Note
import com.example.roomtugas.database.NoteDao
import java.util.concurrent.ExecutorService

class ItemAdapter(private val itemList:ArrayList<Note>) : RecyclerView.Adapter<ItemAdapter
    .ItemHolder>() {

    class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title : TextView = itemView.findViewById(R.id.itemTitle)
        val desc : TextView = itemView.findViewById(R.id.itemDesc)
        val date : TextView = itemView.findViewById(R.id.itemDate)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recyclerview_each_item,
            parent,false)
        return ItemHolder(view)
    }
    override fun getItemCount(): Int {
        return itemList.size
    }
    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = itemList[position]
        holder.title.text = item.title
        holder.desc.text = item.description
        holder.date.text = item.date

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, updateItem::class.java)
            intent.putExtra("itemId",item.id)
            intent.putExtra("itemTitle", item.title.toString())
            intent.putExtra("itemDesc", item.description.toString())
            intent.putExtra("itemDate", item.date.toString())
            holder.itemView.context.startActivity(intent)
        }
    }
}