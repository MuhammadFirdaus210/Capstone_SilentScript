package com.example.silentscript.ui.notifications

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.silentscript.R
import com.example.silentscript.databinding.ActivityDetailVideoBinding
import com.example.silentscript.ui.home.Abjad

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
