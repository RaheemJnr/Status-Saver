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

/**
 * a sealed class to update ui data state
 * **/
@Stable
@Immutable
sealed class UIDataState<T> {
    class Loading<T> : UIDataState<T>()
    data class Success<T>(val data: T) : UIDataState<T>()
    class Failed<T>(val message: T) : UIDataState<T>()

    val isLoading get() = this is Loading

    val isSuccess get() = this is Success

    val isFailed get() = this is Failed

    companion object {
        fun <T> loading() = Loading<T>()
        fun <T> success(data: T) = Success(data)
        fun <T> failed(message: T) = Failed<T>(message)
    }
}

data class UIState(
    val status: ArrayList<Status>? = arrayListOf(),
    val errorMessage: String? = null
)