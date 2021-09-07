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
        setupPauseButton()
    }

    private fun setupPlayButton() {
        binding.playButton.setOnClickListener {
            startMusicPlayerService(PlayerAction.ACTION_PLAY)
        }
    }

    private fun setupPauseButton() {
        binding.pauseButton.setOnClickListener {
            startMusicPlayerService(PlayerAction.ACTION_PAUSE)
        }
    }

    private fun startMusicPlayerService(action: PlayerAction) {
        Intent(this, MusicPlayerService::class.java).apply {
            this.action = action.value
        }.let {
            startService(it)
        }
    }
}
