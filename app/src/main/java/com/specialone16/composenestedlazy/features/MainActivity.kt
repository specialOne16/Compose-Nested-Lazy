package com.specialone16.composenestedlazy.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.media3.common.Player
import com.specialone16.composenestedlazy.features.comments.playerWithComment

class MainActivity : ComponentActivity() {
    var player: Player? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        player = playerWithComment()
    }

    override fun onPause() {
        super.onPause()
        player?.release()
    }
}
