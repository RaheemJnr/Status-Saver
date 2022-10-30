package com.example.statussaver.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.VideoFrameDecoder
import com.example.statussaver.model.Status

@Composable
fun VideoLayout(
    status: Status
) {
    val context = LocalContext.current
    val painterr = ImageLoader.Builder(context)
        .components {
            add(VideoFrameDecoder.Factory())
        }.crossfade(true)
        .build()

    val painter = rememberAsyncImagePainter(
        model = status.file,
        imageLoader = painterr,
        filterQuality = FilterQuality.High
    )


    var editable by rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
    ) {
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
            Image(
                painter = painter,
                contentDescription = "",
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            )
        }

    }
    Box {

        AnimatedVisibility(
            visible = editable
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(
                    Modifier.align(Alignment.Bottom)
                ) {
                    Image(
                        imageVector = Icons.Default.Done,
                        contentDescription = "",
                    )
                }
            }

        }
    }
}