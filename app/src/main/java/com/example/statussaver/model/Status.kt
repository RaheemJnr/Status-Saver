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
//sealed class UIDataState<T> {
//    object Loading : UIDataState<Loading>()
//    data class Success<T>(val data: T) : UIDataState<T>()
//    class Failed<T>(val message: T) : UIDataState<T>()
//
//    val isLoading get() = this is Loading
//
//    val isSuccess get() = this is Success
//
//    val isFailed get() = this is Failed
//
//    companion object {
//        fun <T> loading() = Loading
//        fun <T> success(data: T) = Success(data)
//        fun <T> failed(message: T) = Failed(message)
//    }
//}


/**
 * A sealed hierarchy describing the state of the feed of news resources.
 */
sealed interface UIDataState {
    /**
     * The feed is still loading.
     */
    object Loading : UIDataState

    /**
     * The feed is loaded with the given list of news resources.
     */
    data class Success(
        /**
         * The list of news resources contained in this feed.
         */
        val feed: UIState
    ) : UIDataState

    data class Failed(
        /**
         * The list of news resources contained in this feed.
         */
        val feed: UIState
    ) : UIDataState
}

data class UIState(
    val status: List<Status> = listOf(),
    val errorMessage: String? = null
) {
    companion object {
        val EMPTY = null
    }
}