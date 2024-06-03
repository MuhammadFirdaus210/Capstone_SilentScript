package com.example.silentscript

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.silentscript.databinding.ActivityVideoBinding

class VideoActivity : AppCompatActivity() {

    private lateinit var binding: ActivityVideoBinding
    private lateinit var rvabjad: RecyclerView
    private val list = ArrayList<Abjad>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

            rvabjad = findViewById(R.id.rv_abjad)
            rvabjad.setHasFixedSize(true)

            list.addAll(getListHeroes())
            showRecyclerList()
        }

    private fun getListHeroes(): ArrayList<Abjad> {
        val dataName = resources.getStringArray(R.array.data_abjad)
        val listabjad = ArrayList<Abjad>()
        for (i in dataName.indices) {
            val abjad = Abjad(dataName[i])
            listabjad.add(abjad)
        }
        return listabjad
    }

    private fun showRecyclerList() {
        rvabjad.layoutManager = GridLayoutManager(this, 3)
        val listAbjadAdapter = ListAbjadAdapter(list)
        rvabjad.adapter = listAbjadAdapter
    }
}
