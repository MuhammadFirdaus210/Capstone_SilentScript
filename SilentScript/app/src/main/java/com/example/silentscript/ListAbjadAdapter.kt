package com.example.silentscript

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAbjadAdapter(private val listAbjad: ArrayList<Abjad>) : RecyclerView.Adapter<ListAbjadAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view : View = LayoutInflater.from(parent.context).inflate(R.layout.item_lib_abjad, parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        // Logic to set background color
//        val spanCount = 3  // Number of columns
//        val rowIndex = position / spanCount  // Calculate the row index
//        if (rowIndex % 2 == 0) {
//            holder.tvName.setBackgroundColor(Color.WHITE)  // Set background color to white
//        } else {
//            holder.tvName.setBackgroundColor(Color.BLUE)  // Set background color to blue
//        }

        val (name) =listAbjad[position]
        holder.tvName.text = name
        holder.itemView.setOnClickListener{holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listAbjad[holder.adapterPosition]) }
        }

    }

    override fun getItemCount(): Int = listAbjad.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tv_abjad)
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: Abjad)
    }

}
