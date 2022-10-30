package com.example.statussaver.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.statussaver.model.Status
import java.io.File

@Composable
fun ImageLayout(
    status: Status
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, top = 4.dp, bottom = 4.dp, end = 4.dp)
            .size(100.dp)

    ) {
        Image(
            painter = rememberAsyncImagePainter(model = status.file),
            contentDescription = "",
            contentScale = ContentScale.Fit,
            modifier = Modifier
        )
        Text(text = status.title)
    }
}

val emptyStatus = Status(
    file = File(""),
    title = "",
    path = "",
    isVideo = false,

    )

@Preview(showBackground = true)
@Composable
fun ImageLayoutPreview() {
    ImageLayout(status = emptyStatus)
}