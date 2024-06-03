package com.example.silentscript

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.silentscript.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        onClick()

    }
    private fun onClick(){
        binding.register.setOnClickListener(){
            startActivity(Intent(this, RegisterActivity::class.java))
        }
        binding.login.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}