package com.example.silentscript.ui.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.silentscript.R

class VideoAdapter (private val listVideo: ArrayList<Video>): RecyclerView.Adapter<VideoAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallBack: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallBack = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding: View = LayoutInflater.from(parent.context).inflate(R.layout.item_video, parent, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val video = listVideo[position]
        holder.mnName.text = video.name
        holder.itemView.setOnClickListener {onItemClickCallBack.onItemClicked(listVideo[holder.adapterPosition])}

    }

    override fun getItemCount(): Int = listVideo.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val mnName : TextView = itemView.findViewById(R.id.text_judul_video)
    }
    interface OnItemClickCallback {
        fun onItemClicked(data: Video)
    }
}