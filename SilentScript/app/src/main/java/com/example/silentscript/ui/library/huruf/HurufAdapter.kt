package com.example.silentscript.ui.library.huruf

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.silentscript.R
import com.example.silentscript.databinding.ItemGameBinding
import com.example.silentscript.databinding.ItemLibAbjadBinding
import com.example.silentscript.ui.game.GameDetailActivity
import com.example.silentscript.ui.library.data.response.GameResponse
import com.example.silentscript.ui.library.data.response.HurufResponse
import com.example.silentscript.ui.library.data.response.KataResponse
import com.example.silentscript.ui.library.kata.DetailActivity

class HurufAdapter(private var hurufList: List<HurufResponse>) : RecyclerView.Adapter<HurufAdapter.HurufViewHolder>() {

    class HurufViewHolder(private val binding: ItemLibAbjadBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(huruf: HurufResponse) {
            binding.tvAbjad.text = huruf.penjelasan
            // If you have an ImageView or other views in your layout, bind them here.

            // Add OnClickListener to navigate to GameDetailActivity
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, DetailAbjadActivity::class.java)
                Log.d("HurufAdapter", "id: ${huruf.id}")
                intent.putExtra("name", huruf.penjelasan)
                intent.putExtra("id", huruf.id)
                intent.putExtra("image", huruf.image)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HurufViewHolder {
        val binding = ItemLibAbjadBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HurufViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HurufViewHolder, position: Int) {
        holder.bind(hurufList[position])
    }

    override fun getItemCount() = hurufList.size

    fun updateGameList(newGameList: List<HurufResponse>) {
        hurufList = newGameList
        notifyDataSetChanged()
    }
}