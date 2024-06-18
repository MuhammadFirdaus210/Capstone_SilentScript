package com.example.silentscript.ui.library.kata

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.silentscript.databinding.ActivityDetailBinding
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var player: ExoPlayer
    private lateinit var video: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val kata: String = intent.getStringExtra("kata")!!
        binding.judulKata.text = kata

        video = intent.getStringExtra("video")!!

        binding.back.setOnClickListener {
            finish()
        }
    }

    override fun onStart() {
        super.onStart()
        try {
            player = SimpleExoPlayer.Builder(this).build()
            binding.videoView.player = player

            // Siapkan media item
            val mediaItem = MediaItem.fromUri(video)
            player.setMediaItem(mediaItem)
            player.prepare()

            binding.playButton.setOnClickListener {
                if (player.isPlaying) {
                    player.pause()
                    binding.playButton.visibility = View.VISIBLE
                } else {
                    if (player.playbackState == Player.STATE_ENDED) {
                        player.seekTo(0)
                    }
                    player.play()
                    binding.playButton.visibility = View.GONE
                }
            }

            // Tambahkan listener ke player
            player.addListener(object : Player.Listener {
                override fun onPlaybackStateChanged(state: Int) {
                    if (state == Player.STATE_ENDED) {
                        // Video selesai diputar, tampilkan tombol play
                        binding.playButton.visibility = View.VISIBLE
                    }
                }
            })

        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
            // Log the exception or handle the error in some way
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}