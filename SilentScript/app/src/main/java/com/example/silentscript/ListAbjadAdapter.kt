package com.example.silentscript


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAbjadAdapter(private val listAbjad: ArrayList<Abjad>): RecyclerView.Adapter<ListAbjadAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallBack: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallBack = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding: View = LayoutInflater.from(parent.context).inflate(R.layout.item_lib_abjad, parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val abjad = listAbjad[position]
        holder.mnName.text = abjad.name
        holder.itemView.setOnClickListener {onItemClickCallBack.onItemClicked(listAbjad[holder.adapterPosition])}

    }

    override fun getItemCount(): Int = listAbjad.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mnName : TextView = itemView.findViewById(R.id.tv_abjad)
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: Abjad)
    }
}