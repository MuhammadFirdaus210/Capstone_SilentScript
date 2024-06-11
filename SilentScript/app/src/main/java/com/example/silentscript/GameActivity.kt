package com.example.silentscript

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.silentscript.databinding.ActivityAbjadBinding
import com.example.silentscript.databinding.ActivityGameBinding
import com.example.silentscript.ui.home.Abjad
import com.example.silentscript.ui.home.DetailAbjadActivity
import com.example.silentscript.ui.home.ListAbjadAdapter

class GameActivity : AppCompatActivity() {
   private lateinit var binding: ActivityGameBinding

    private lateinit var rvabjad: RecyclerView
    private val list = ArrayList<Abjad>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        rvabjad = findViewById(R.id.rv_game)
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
        rvabjad.layoutManager = GridLayoutManager(this, 2)
        val listAbjadAdapter = GameAdapter(list)
        rvabjad.adapter = listAbjadAdapter

        listAbjadAdapter.setOnItemClickCallback(object : GameAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Abjad) {
                showSelectedHero(data)
            }
        })

    }
    private fun showSelectedHero(data: Abjad) {
        val intent = Intent(this, DetailAbjadActivity::class.java)
        intent.putExtra("food_data", data)
        startActivity(intent)
    }
}
