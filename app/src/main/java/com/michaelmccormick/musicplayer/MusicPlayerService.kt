package com.michaelmccormick.musicplayer

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import android.os.PowerManager

class MusicPlayerService : Service(), MediaPlayer.OnPreparedListener {
    private var mediaPlayer: MediaPlayer? = null

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        when (intent.action) {
            ACTION_PLAY -> {
                // Cleanup existing player in case it's already playing
                cleanupPlayer()
                mediaPlayer = createMediaPlayer()
            }
        }
        // Returning START_REDELIVER_INTENT so that if service is killed
        // we get relaunched with the original intent
        return START_REDELIVER_INTENT
    }

    override fun onPrepared(mediaPlayer: MediaPlayer) {
        mediaPlayer.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        cleanupPlayer()
    }

    override fun onBind(intent: Intent?): IBinder? = null

    private fun createMediaPlayer() = MediaPlayer().apply {
        setAudioAttributes(
            AudioAttributes.Builder()
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                .setUsage(AudioAttributes.USAGE_MEDIA)
                .build()
        )
        setOnPreparedListener(this@MusicPlayerService)
        // Keeps the CPU awake when service is running in the background
        setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
        // Set the media data source
        setDataSource(applicationContext, buildUri())
        // Prepare media on the backing thread
        prepareAsync()
    }

    // Creating URI from resource to simulate file stored locally/remotely
    private fun buildUri() = Uri.parse("android.resource://$packageName/${R.raw.bensound_slowmotion}")

    private fun cleanupPlayer() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
    }

    companion object {
        const val ACTION_PLAY = "com.michaelmccormick.musicplayer.PLAY"
    }
}
