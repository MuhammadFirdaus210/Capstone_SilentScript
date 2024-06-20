package com.example.silentscript

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.silentscript.databinding.ActivityGetStartedBinding

class GetStarted : AppCompatActivity() {
    private lateinit var binding: ActivityGetStartedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_get_started)
        super.onCreate(savedInstanceState)
        binding = ActivityGetStartedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        onClick()
    }

    private fun onClick() {
        binding.btnStart.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}