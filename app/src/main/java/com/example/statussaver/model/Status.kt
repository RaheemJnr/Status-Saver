package com.example.statussaver.model

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import java.io.File

const val MP4 = ".mp4"

@Stable
@Immutable
data class Status(
    val file: File,
    val title: String,
    val path: String,
    val isVideo: Boolean = file.name.endsWith(MP4)
)