package com.example.silentscript.ui.library.huruf

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.silentscript.databinding.ActivityHurufBinding
import com.example.silentscript.ui.library.KataViewModel
import com.example.silentscript.ui.library.kata.KataAdapter

class HurufActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHurufBinding
    private lateinit var rvabjad: RecyclerView
    private val viewModel: HurufViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHurufBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        val slug: String = intent.getStringExtra("USER_ID")!!
        binding.textId.text = slug

        val judul: String = intent.getStringExtra("judul")!!
        binding.textJudul.text = judul

        binding.rvAbjad.layoutManager = GridLayoutManager(this, 3)

        binding.back.setOnClickListener(){
            finish()
        }
        // Fetch the library data
        viewModel.fetchLibrary(slug)

        // Observe the library LiveData
        viewModel.library.observe(this) { library ->
            // Update the RecyclerView when the data changes
            val kataAdapter = HurufAdapter(library)
            binding.rvAbjad.adapter = kataAdapter
            kataAdapter.notifyDataSetChanged()
        }
    }
}