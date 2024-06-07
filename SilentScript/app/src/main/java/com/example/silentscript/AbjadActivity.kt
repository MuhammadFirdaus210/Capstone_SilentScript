package com.example.silentscript

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.silentscript.databinding.ActivityAbjadBinding

class AbjadActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAbjadBinding
    private lateinit var rvabjad: RecyclerView
    private val list = ArrayList<Abjad>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAbjadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

            rvabjad = findViewById(R.id.rv_abjad)
            rvabjad.setHasFixedSize(true)

            list.addAll(getListHeroes())
            showRecyclerList()

            onClick()
        }

    private fun onClick(){
        binding.back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
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

        listAbjadAdapter.setOnItemClickCallback(object : ListAbjadAdapter.OnItemClickCallback {
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
