package com.example.statussaver.model

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