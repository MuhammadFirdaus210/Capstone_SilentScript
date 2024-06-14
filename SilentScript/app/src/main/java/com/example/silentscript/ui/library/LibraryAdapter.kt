package com.example.silentscript.ui.library

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.silentscript.R
import com.example.silentscript.ui.library.data.response.LibraryResponse

class LibraryAdapter(private val libraryList: List<LibraryResponse>) : RecyclerView.Adapter<LibraryAdapter.LibraryViewHolder>() {

    var onItemClick: ((LibraryResponse) -> Unit)? = null

    inner class LibraryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val judulTextView: TextView = view.findViewById(R.id.judul_lib)
        val keteranganTextView: TextView = view.findViewById(R.id.jumlah_lib)

        init {
            view.setOnClickListener {
                onItemClick?.invoke(libraryList[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LibraryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return LibraryViewHolder(view)
    }

    override fun onBindViewHolder(holder: LibraryViewHolder, position: Int) {
        val libraryItem = libraryList[position]
        holder.judulTextView.text = libraryItem.kategori
        holder.keteranganTextView.text = libraryItem.keterangan
    }

    override fun getItemCount() = libraryList.size
}