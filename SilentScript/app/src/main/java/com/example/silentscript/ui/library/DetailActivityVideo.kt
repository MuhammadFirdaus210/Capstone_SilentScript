package com.example.silentscript.ui.library

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.silentscript.R
import com.example.silentscript.databinding.ActivityDetailVideoBinding

class DetailActivityVideo : AppCompatActivity() {

    private lateinit var binding: ActivityDetailVideoBinding
    private lateinit var nmDetail: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        nmDetail = findViewById(R.id.judul_video)
        val resultAbjad = intent.getParcelableExtra<Video>("video_data")

        if (resultAbjad != null){
            nmDetail.text = resultAbjad.name
        }
    }
}
