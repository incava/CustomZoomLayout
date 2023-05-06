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
        // 키오스크 Lock모드
        startLockTask()
    }

    val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        //윈도우 화면 제한 없애기
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        // 제생 플레이어
        val player = ExoPlayer.Builder(this).build()
        val player2 = ExoPlayer.Builder(this).build()
        val player3 = ExoPlayer.Builder(this).build()
        Log.i("layoutsize",screenXSize().toString())
        Log.i("layoutsize",screenYSize().toString())

        // screenSize에 맞게 설정
        binding.playerView.player = player
        binding.playerView.layoutParams.width = screenXSize() ?: 0
        binding.playerView.layoutParams.height = screenYSize() ?: 0
        initSetPlayer(player)

        // 여러 개 플레이어 재생
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

    // 클릭 시, actionUp 클릭에만 반응 하도록 하는 메서드.
    fun clickEvent(v : View, event : MotionEvent) : Boolean{
        Log.i("touch","${v} + ${event.action}")
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                // Move 이벤트는 무시.
                return false
            }
            MotionEvent.ACTION_DOWN -> {
                //Down 이벤트를 받아야 Up이 실행됨. 단순 받기만 하도록 true
                return true
            }
            MotionEvent.ACTION_UP -> {
                // Up 이벤트는 클릭 이벤트로 볼 수 있음.
                performClick()
                return true
            }
            else -> return false
        }
    }
}