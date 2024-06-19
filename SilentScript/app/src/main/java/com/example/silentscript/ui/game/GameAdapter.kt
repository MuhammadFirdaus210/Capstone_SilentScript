package com.example.silentscript.ui.game

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.silentscript.databinding.ItemGameBinding
import com.example.silentscript.ui.library.data.response.GameResponse
import kotlin.math.log

class GameAdapter(private var gameList: List<GameResponse>) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    class GameViewHolder(private val binding: ItemGameBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(game: GameResponse) {
            binding.judulGame.text = game.level
            // If you have an ImageView or other views in your layout, bind them here.

            // Add OnClickListener to navigate to GameDetailActivity
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, GameDetailActivity::class.java)
                intent.putExtra("levelId", game.levelId)
                intent.putExtra("level", game.level)
                intent.putExtra("image", game.image)
                itemView.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = ItemGameBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return GameViewHolder(binding)
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.bind(gameList[position])
    }

    override fun getItemCount() = gameList.size

    fun updateGameList(newGameList: List<GameResponse>) {
        gameList = newGameList
        notifyDataSetChanged()
    }
}