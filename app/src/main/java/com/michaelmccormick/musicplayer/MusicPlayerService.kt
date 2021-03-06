package com.michaelmccormick.musicplayer

import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.Uri
import android.os.IBinder
import android.os.PowerManager
import android.util.Log

class MusicPlayerService : Service(), MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
    private var mediaPlayer: MediaPlayer? = null
    private var isPaused: Boolean = false

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        when (intent.action) {
            PlayerAction.ACTION_PLAY.value -> {
                if (isPaused) {
                    isPaused = false
                    mediaPlayer?.start()
                } else {
                    // Cleanup existing player in case it's currently playing
                    cleanupPlayer()
                    mediaPlayer = createMediaPlayer()
                }
            }
            PlayerAction.ACTION_PAUSE.value -> {
                isPaused = true
                mediaPlayer?.pause()
            }
        }
        // Returning START_REDELIVER_INTENT so that if service is killed we get relaunched with the original intent
        return START_REDELIVER_INTENT
    }

    override fun onPrepared(mediaPlayer: MediaPlayer) {
        mediaPlayer.start()
    }

    override fun onError(mediaPlayer: MediaPlayer, what: Int, extra: Int): Boolean {
        mediaPlayer.stop()
        mediaPlayer.release()
        Log.e(MusicPlayerService::class.java.name, "$what $extra")
        return true
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
}
