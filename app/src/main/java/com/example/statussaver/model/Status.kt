package com.example.statussaver.model

import androidx.compose.ui.graphics.vector.ImageVector
import java.io.File

const val MP4 = ".mp4"

data class Status(
    val file: File,
    val title: String,
    val path: String,
    var isVideo: Boolean = file.name.endsWith(MP4)
)

val emptyStatus = Status(
    file = File(""),
    title = "",
    path = "",
    isVideo = false,

    )

data class Statuss(
    val file: ImageVector,
    val title: String,
    var isVideo: Boolean = file.name.endsWith(MP4)
)