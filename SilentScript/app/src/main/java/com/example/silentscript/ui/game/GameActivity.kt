package com.example.silentscript.ui.game

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.silentscript.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityGameBinding
    private lateinit var viewModel: GameViewModel
    private lateinit var adapter: GameAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        viewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        setupRecyclerView()

        viewModel.fetchGame()

        viewModel.game.observe(this, { gameList ->
            adapter.updateGameList(gameList)
        })

        viewModel.isLoading.observe(this) { isLoading -> // Changed viewLifecycleOwner to this
            binding.isLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        binding.back.setOnClickListener {
            finish()
        }
    }
    private fun setupRecyclerView() {
        adapter = GameAdapter(listOf())
        binding.rvGame.layoutManager = GridLayoutManager(this, 2)
        binding.rvGame.adapter = adapter
    }
}