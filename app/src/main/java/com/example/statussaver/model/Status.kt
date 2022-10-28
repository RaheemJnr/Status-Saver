package com.example.statussaver.model

import java.io.File

data class Status(
    val file: File,
    val title: String,
    val path: String,
    var isVideo:Boolean

) {
    init {
        val MP4 = ".mp4"
        isVideo = file.name.endsWith(MP4)
    }
}

