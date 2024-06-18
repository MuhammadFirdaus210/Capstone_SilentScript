package com.example.silentscript.ui.game

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.silentscript.databinding.ActivityGameDetailBinding
import com.example.silentscript.ui.game.gamestart.StartGameActivity

class GameDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGameDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val levelId = intent.getIntExtra("levelId", 0)

        binding.gameId.text = levelId.toString()

        binding.back.setOnClickListener {
            finish()
        }

        binding.btnStart.setOnClickListener {
            val intent = Intent(this, StartGameActivity::class.java)
            Log.d("GameDetailActivity", "levelId: $levelId")
            intent.putExtra("levelId", levelId) // Pass the levelId to StartGameActivity
            startActivity(intent)
        }
    }
}