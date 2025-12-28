import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import androidx.compose.ui.platform.LocalContext

@Composable
fun VoiceMessage(url: String) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(false) }

    // Create player
    val player = remember {
        ExoPlayer.Builder(context).build().apply {
            setMediaItem(MediaItem.fromUri(Uri.parse(url)))
            prepare()
        }
    }

    DisposableEffect(player) {
        onDispose {
            player.release()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .background(Color.DarkGray)
            .padding(8.dp)
            .clickable {
                if (isPlaying) {
                    player.pause()
                    isPlaying = false
                } else {
                    player.play()
                    isPlaying = true
                }
            }
    ) {
        Text(
            text = if (isPlaying) "Playing..." else "Play Voice",
            color = Color.White,
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }
}
