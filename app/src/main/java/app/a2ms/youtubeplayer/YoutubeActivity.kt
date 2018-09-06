package app.a2ms.youtubeplayer

import android.annotation.SuppressLint
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.util.Log
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

const val YOUTUBE_VIDEO_ID = "watch?v=lW2PI6jqn1U"
const val YOUTUBE_PLAYLIST = "watch?v=8JnfIa84TnU&list=PL4o29bINVT4EG_y-k5jGoOu3-Am8Nvi10"

class YoutubeActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    private val mTAG = "Youtube Activity"
    @SuppressLint("InflateParams", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layout = layoutInflater.inflate(R.layout.activity_youtube, null) as ConstraintLayout
        setContentView(layout)

        //Add playerView programmatically
        val playerView = YouTubePlayerView(this)
        playerView.layoutParams = ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT
        )
        layout.addView(playerView)

        //initializing the playerView
        playerView.initialize(getString(R.string.GOOGLE_API_KEY), this)
    }

    override fun onInitializationSuccess(provider: YouTubePlayer.Provider?, youTubePlayer: YouTubePlayer?, wasRestored: Boolean) {
        Log.d(mTAG, "onInitializationSuccess: provider is ${provider?.javaClass}")
        Log.d(mTAG, "onInitializationSuccess: Youtube Player is ${youTubePlayer?.javaClass}")
        Toast.makeText(this, "Initialized Youtube Player successfully", Toast.LENGTH_SHORT).show()
        youTubePlayer?.setPlayerStateChangeListener(playerStateChangeListener)
        youTubePlayer?.setPlaybackEventListener(playBackEventListener)
        if (!wasRestored) {
            youTubePlayer?.loadVideo(YOUTUBE_VIDEO_ID)
        } else {
            youTubePlayer?.play()
        }
    }

    override fun onInitializationFailure(provider: YouTubePlayer.Provider?,
                                         youTubeInitializationResult: YouTubeInitializationResult?) {
        val REQUEST_CODE = 0
        if (youTubeInitializationResult?.isUserRecoverableError == true) {
            youTubeInitializationResult.getErrorDialog(this, REQUEST_CODE)
        } else {
            val errorMessage = "There was an error initializing the YoutubePlayer ($youTubeInitializationResult)"
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private val playBackEventListener = object : YouTubePlayer.PlaybackEventListener {
        override fun onSeekTo(p0: Int) {
        }

        override fun onBuffering(p0: Boolean) {
        }

        override fun onPlaying() {
            Toast.makeText(this@YoutubeActivity, "Playing OK", Toast.LENGTH_SHORT).show()
        }

        override fun onStopped() {
        }

        override fun onPaused() {
            Toast.makeText(this@YoutubeActivity, "Paused", Toast.LENGTH_SHORT).show()
        }

    }
    private val playerStateChangeListener = object : YouTubePlayer.PlayerStateChangeListener {
        override fun onAdStarted() {
            Toast.makeText(this@YoutubeActivity, "Click Ad now and make me rich ", Toast.LENGTH_SHORT).show()

        }

        override fun onLoading() {
        }

        override fun onVideoStarted() {
            Toast.makeText(this@YoutubeActivity, "Started", Toast.LENGTH_SHORT).show()

        }

        override fun onLoaded(p0: String?) {
        }

        override fun onVideoEnded() {
            Toast.makeText(this@YoutubeActivity, "Ended", Toast.LENGTH_SHORT).show()

        }

        override fun onError(p0: YouTubePlayer.ErrorReason?) {
        }

    }
}

