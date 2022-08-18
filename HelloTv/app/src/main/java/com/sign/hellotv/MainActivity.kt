package com.sign.hellotv

import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import com.sign.hellotv.databinding.ActivityMainBinding
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer

class MainActivity : FragmentActivity() {

    companion object {

        private const val USE_TEXTURE_VIEW = false
        private const val ENABLE_SUBTITLES = true
    }

    private lateinit var binding: ActivityMainBinding

    private var mLibVLC: LibVLC? = null
    private var mMediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initPlayerView()
    }

    private fun initPlayerView() {
        val filePath = "https://huawei.dailyyoga.com.cn/fullbodypowerflow1548753401799"
        mLibVLC = LibVLC(this, ArrayList<String>().apply {
            add("--no-drop-late-frames")
            add("--no-skip-frames")
            add("--rtsp-tcp")
            add("-vvv")
        })
        mMediaPlayer = MediaPlayer(mLibVLC)
        mMediaPlayer?.attachViews(binding.viewVlcLayout, null, ENABLE_SUBTITLES, USE_TEXTURE_VIEW)
        try {
            Media(mLibVLC, Uri.parse(filePath)).apply {
                setHWDecoderEnabled(true, true);
                // addOption(":network-caching=150");
                // addOption(":clock-jitter=0");
                // addOption(":clock-synchro=0");
                mMediaPlayer?.media = this
            }.release()
            mMediaPlayer?.play()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onStop() {
        super.onStop()
        mMediaPlayer?.stop()
        mMediaPlayer?.detachViews()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMediaPlayer?.release()
        mLibVLC?.release()
    }
}