package com.example.silentscript

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.silentscript.ui.home.Abjad

class GameAdapter (private val listAbjad: ArrayList<Abjad>): RecyclerView.Adapter<GameAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallBack: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallBack = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding: View = LayoutInflater.from(parent.context).inflate(R.layout.item_game, parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val abjad = listAbjad[position]
        holder.mnName.text = abjad.name
        holder.itemView.setOnClickListener {onItemClickCallBack.onItemClicked(listAbjad[holder.adapterPosition])}

    }

    override fun getItemCount(): Int = listAbjad.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mnName : TextView = itemView.findViewById(R.id.judul_game)
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: Abjad)
    }
}