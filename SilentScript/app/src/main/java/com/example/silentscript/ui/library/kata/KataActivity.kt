package com.example.silentscript.ui.library.kata

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.silentscript.databinding.ActivityKataBinding
import com.example.silentscript.ui.library.KataViewModel

class KataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityKataBinding
    private val viewModel: KataViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityKataBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val slug: String = intent.getStringExtra("slug")!!
        binding.textId.text = slug

        val judul: String = intent.getStringExtra("judul")!!
        binding.textJudul.text = judul

        // Initialize the RecyclerView
        binding.rvKata.layoutManager = LinearLayoutManager(this)

        binding.back.setOnClickListener(){
            finish()
        }


        // Fetch the library data
        viewModel.fetchLibrary(slug)

        // Observe the library LiveData
        viewModel.library.observe(this) { library ->
            // Update the RecyclerView when the data changes
            val kataAdapter = KataAdapter(library)
            binding.rvKata.adapter = kataAdapter
            kataAdapter.notifyDataSetChanged()
        }
    }
}