package Yoga

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.randomuser.R
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer

import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import kotlinx.android.synthetic.main.activity_player.*


class PlayerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        lifecycle.addObserver(youtube_player_view)

        youtube_player_view.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                val videoId = "S3pYYNrbtPo"
                val videoId2 = "rsuO6K2RUtI"

                youTubePlayer.loadVideo(videoId, 0f)
                youTubePlayer.loadVideo(videoId2, 0f)
               onVideoDuration( youTubePlayer, duration=0f)
//                youtube_player_view.enterFullScreen()
//                youtube_player_view.exitFullScreen()
              youtube_player_view.isFullScreen()
            //    youtube_player_view.toggleFullScreen()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        youtube_player_view.release()
    }
}