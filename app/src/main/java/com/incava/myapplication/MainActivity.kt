package com.incava.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.WindowManager
import android.widget.Toast
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.RawResourceDataSource
import com.google.android.exoplayer2.video.VideoSize
import com.incava.myapplication.databinding.ActivityMainBinding
import com.otaliastudios.zoom.ZoomSurfaceView

class MainActivity : AppCompatActivity() {
    override fun onStart() {
        super.onStart()
        startLockTask()
    }


    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        val player = ExoPlayer.Builder(this).build()
        val player2 = ExoPlayer.Builder(this).build()
        val player3 = ExoPlayer.Builder(this).build()
        Log.i("layoutsize",screenXSize().toString())
        Log.i("layoutsize",screenYSize().toString())
        binding.playerView.player = player
        binding.playerView.layoutParams.width = screenXSize() ?: 0
        binding.playerView.layoutParams.height = screenYSize() ?: 0
        initSetPlayer(player)
        binding.layout.playerView2.player = player2
        binding.layout.playerView2.setOnTouchListener {v, event->
            clickEvent(v, event)
        }


        initSetPlayer(player2)
        binding.layout2.playerView3.player = player3
        binding.layout2.playerView3.setOnTouchListener{v, event->
            clickEvent(v, event)}
        initSetPlayer(player3)
        //frame1Binding.playerView.player = player
    }

    fun initSetPlayer(player : Player){
        val resourceId = R.raw.sample // 로컬 파일의 리소스 ID
        val rawResourceDataSource = RawResourceDataSource(this@MainActivity)
        rawResourceDataSource.open(DataSpec(RawResourceDataSource.buildRawResourceUri(resourceId)))
        val mediaItem = MediaItem.fromUri(rawResourceDataSource.uri!!)
        player.setMediaItem(mediaItem)
        player.repeatMode = Player.REPEAT_MODE_ONE
        player.prepare()
        player.play()
        //Log.i("screenSizeX", screenXSize().toString())
        //Log.i("screenSizeY", screenYSize().toString())
        Log.i("player", player.videoSize.width.toString())
        Log.i("player", player.videoSize.height.toString())
    }

    private fun screenXSize() =
        applicationContext?.resources?.displayMetrics?.widthPixels //화면 사이즈 구하기.

    private fun screenYSize() =
        applicationContext?.resources?.displayMetrics?.heightPixels //화면 사이즈 구하기.

    fun performClick(){
        Toast.makeText(this, "클릭 이벤트!", Toast.LENGTH_SHORT).show()
    }

    fun clickEvent(v : View, event : MotionEvent) : Boolean{
        Log.i("touch","${v} + ${event.action}")
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                // Move 이벤트는 무시합니다.
                return false
            }
            MotionEvent.ACTION_DOWN -> {
                // Down 이벤트는 다른 View에서 처리하도록 넘깁니다.
                return false
            }
            MotionEvent.ACTION_UP -> {
                // Up 이벤트는 클릭 이벤트로 처리합니다.
                performClick()
                return true
            }
            else -> return false
        }
    }
}