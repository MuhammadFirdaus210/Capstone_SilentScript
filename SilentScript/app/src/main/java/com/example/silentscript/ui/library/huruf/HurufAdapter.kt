package com.example.silentscript.ui.library.huruf

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.silentscript.R
import com.example.silentscript.ui.library.data.response.HurufResponse
import com.example.silentscript.ui.library.data.response.KataResponse
import com.example.silentscript.ui.library.kata.DetailActivity

class HurufAdapter(var hurufList: List<HurufResponse>) : RecyclerView.Adapter<HurufAdapter.HurufViewHolder>() {

    var onItemClick: ((HurufResponse) -> Unit)? = null

    inner class HurufViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val judulTextView: TextView = view.findViewById(R.id.tv_abjad)
        init {
            view.setOnClickListener {
                val kataItem = hurufList[adapterPosition]
                val intent = Intent(view.context, DetailActivity::class.java)
                intent.putExtra("kata", kataItem.penjelasan)
                view.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HurufViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_lib_abjad, parent, false)
        return HurufViewHolder(view)
    }

    override fun onBindViewHolder(holder: HurufViewHolder, position: Int) {
        val kataItem = hurufList[position]
        holder.judulTextView.text = kataItem.penjelasan
    }

    override fun getItemCount() = hurufList.size
}