package com.example.silentscript

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.silentscript.databinding.ActivityDetailAbjadBinding

class DetailAbjadActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailAbjadBinding
    private lateinit var nmDetail: TextView
    private lateinit var nmDetail2: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_abjad)
        supportActionBar?.hide()

        nmDetail = findViewById(R.id.top)
        nmDetail2 = findViewById(R.id.bottom)
        val resultAbjad = intent.getParcelableExtra<Abjad>("food_data")

        if (resultAbjad != null){
            nmDetail.text = resultAbjad.name
            nmDetail2.text = resultAbjad.name
        }
    }
}