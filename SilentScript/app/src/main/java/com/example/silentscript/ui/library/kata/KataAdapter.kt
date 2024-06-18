package com.example.silentscript.ui.library.kata

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.silentscript.R
import com.example.silentscript.ui.library.data.response.KataResponse

class KataAdapter(var kataList: List<KataResponse>) : RecyclerView.Adapter<KataAdapter.KataViewHolder>() {

    var onItemClick: ((KataResponse) -> Unit)? = null

    inner class KataViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val judulTextView: TextView = view.findViewById(R.id.judul_kata)
        init {
            view.setOnClickListener {
                val kataItem = kataList[adapterPosition]
                val intent = Intent(view.context, DetailActivity::class.java)
                intent.putExtra("kata", kataItem.penjelasan)
                intent.putExtra("video", kataItem.video)
                view.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): KataViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_kata, parent, false)
        return KataViewHolder(view)
    }

    override fun onBindViewHolder(holder: KataViewHolder, position: Int) {
        val kataItem = kataList[position]
        holder.judulTextView.text = kataItem.penjelasan

    }

    override fun getItemCount() = kataList.size
}