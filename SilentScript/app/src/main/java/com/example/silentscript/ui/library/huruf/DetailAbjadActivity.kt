package com.example.silentscript.ui.library.huruf

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.silentscript.R
import com.example.silentscript.databinding.ActivityDetailAbjadBinding

class DetailAbjadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAbjadBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailAbjadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val kata: String = intent.getStringExtra("name")!!
        val imageUri: String = intent.getStringExtra("image")!!

        binding.top.text = kata
        binding.bottom.text = kata
        binding.back.setOnClickListener(){
            finish()
        }

        Glide.with(this)
            .load(imageUri)
            .into(binding.imageHuruf)
    }
}
