package com.specialone16.composenestedlazy.features.comments

import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.specialone16.composenestedlazy.ui.theme.ComposeNestedLazyTheme

fun ComponentActivity.playerWithComment(): Player {
    val player: Player = ExoPlayer.Builder(this).build()
    player.addMediaItem(
        MediaItem.fromUri(
            Uri.parse(
                "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
            )
        )
    )
    player.prepare()
    player.playWhenReady = true

    setContent {
        ComposeNestedLazyTheme {
            PlayerWithComment(playerView = player)
        }
    }

    return player
}

const val COMMENT_COUNT = 500
const val REPLY_COUNT = 500

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PlayerWithComment(playerView: Player) {
    var inputText by remember { mutableStateOf("") }
    Column {
        AndroidView(
            factory = { context ->
                PlayerView(context).also {
                    it.player = playerView
                }
            },
            modifier = Modifier.aspectRatio(16 / 9f)
        )
        CommentImplementation(
            attr = CommentImplementationAttr.PartialFlattenComment(
                items = List(COMMENT_COUNT) { komentarIdx ->
                    Komentar(
                        nama = "a_nice_user_$komentarIdx",
                        pesan = "this is a nice comment",
                        balasan = List(REPLY_COUNT) { balasanIdx ->
                            BalasanKomentar(
                                nama = "an_even_nicer_user_$komentarIdx-$balasanIdx",
                                pesan = "this is a nice reply"
                            )
                        }
                    )
                }
            ),
            modifier = Modifier.weight(1f)
        )
        TextField(
            value = inputText,
            onValueChange = { inputText = it },
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text(text = "Type here...") }
        )
    }
}
