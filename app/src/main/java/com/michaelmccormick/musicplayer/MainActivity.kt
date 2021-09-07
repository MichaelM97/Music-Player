package com.michaelmccormick.musicplayer

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.michaelmccormick.musicplayer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupPlayButton()
    }

    private fun setupPlayButton() {
        binding.playButton.setOnClickListener {
            startMusicPlayerService()
        }
    }

    private fun startMusicPlayerService() {
        Intent(this, MusicPlayerService::class.java).apply {
            this.action = MusicPlayerService.ACTION_PLAY
        }.let {
            startService(it)
        }
    }
}
