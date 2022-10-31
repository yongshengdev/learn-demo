package com.sign.hellotv

import android.net.Uri
import android.os.Bundle
import android.view.SurfaceHolder
import androidx.fragment.app.FragmentActivity
import com.sign.hellotv.databinding.ActivitySurfaceBinding
import org.videolan.libvlc.LibVLC
import org.videolan.libvlc.Media
import org.videolan.libvlc.MediaPlayer
import org.videolan.libvlc.interfaces.IVLCVout

class SurfaceActivity : FragmentActivity() {

    private lateinit var binding: ActivitySurfaceBinding

    private var mLibVLC: LibVLC? = null
    private var mMediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySurfaceBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mLibVLC = LibVLC(this, ArrayList<String>().apply {
//            add("--no-drop-late-frames")
//            add("--no-skip-frames")
//            add("--rtsp-tcp")
            add("-vvv")
        })

        binding.surfaceVideo.holder.addCallback(object : SurfaceHolder.Callback{
            override fun surfaceCreated(sh: SurfaceHolder) {
                val filePath = "https://huawei.dailyyoga.com.cn/fullbodypowerflow1548753401799"
                mMediaPlayer = MediaPlayer(mLibVLC)
                mMediaPlayer!!.vlcVout.setVideoSurface(sh.surface, null)
                mMediaPlayer!!.vlcVout.attachViews { vlcVout, width, height, visibleWidth, visibleHeight, sarNum, sarDen -> }
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

            override fun surfaceChanged(p0: SurfaceHolder, p1: Int, p2: Int, p3: Int) {

            }

            override fun surfaceDestroyed(p0: SurfaceHolder) {

            }

        })
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