package com.example.statussaver.ui.components

import android.widget.Toast
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.VideoFrameDecoder
import com.example.statussaver.R
import com.example.statussaver.model.Status

@Composable
fun VideoLayout(
    status: Status
) {
    val context = LocalContext.current
    var editable by rememberSaveable { mutableStateOf(false) }

    val imageLoader = ImageLoader.Builder(context)
        .components {
            add(VideoFrameDecoder.Factory())
        }.crossfade(true)
        .build()

    val painter = rememberAsyncImagePainter(
        model = status.file,
        imageLoader = imageLoader,
        filterQuality = FilterQuality.High
    )
    Column(
        modifier = Modifier
            .size(128.dp)
            .fillMaxSize()
            .padding(start = 8.dp, top = 4.dp, bottom = 4.dp, end = 4.dp)
            .background(Color.Transparent, shape = RoundedCornerShape(6.dp))
            .clickable {
                editable = !editable
            }
            .border(1.3.dp, Color.Blue, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp))
            .clipToBounds()

    ) {
        Box(
            modifier = Modifier
        ) {
            Image(
                painter = painter,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            )
            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                androidx.compose.animation.AnimatedVisibility(
                    visible = editable,
                    enter = slideInVertically(initialOffsetY = { it }),
                    exit = slideOutVertically(targetOffsetY = { it }),
                    content = {
                        Box(
                            contentAlignment = Alignment.BottomCenter,
                            modifier = Modifier.align(Alignment.BottomCenter)
                                .alpha(0.4f)
                        ) {

                            Row(
                                verticalAlignment = Alignment.Bottom,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(35.dp)
                                   // .alpha(0.4f)
                                    .background(Color.Black)


                            ) {
                                Image(
                                    painter = painterResource(id = R.drawable.download_icon),
                                    contentDescription = "",
                                    colorFilter = ColorFilter.tint(Color.White),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .size(30.dp)
                                        .clickable {
                                            Toast
                                                .makeText(context, "clicked", Toast.LENGTH_SHORT)
                                                .show()
                                        },
                                    alpha = 1f,
                                )
                            }

                        }
                    }
                )
            }
        }

    }
}