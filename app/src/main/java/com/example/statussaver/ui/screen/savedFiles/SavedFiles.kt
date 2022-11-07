package com.example.statussaver.ui.screen.savedFiles

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.StrictMode
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import com.example.statussaver.R
import com.example.statussaver.model.Status
import com.example.statussaver.ui.components.ImageLayout
import com.example.statussaver.ui.components.VideoLayout
import com.example.statussaver.viewmodel.MainViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState


@Composable
fun SavedFileScreen(
    mainViewModel: MainViewModel,
) {
    val savedStatus = mainViewModel.savedStatus.observeAsState()
    mainViewModel.getSavedFiles()
    val isRefreshing = mainViewModel.isRefreshing
    val context = (LocalContext.current) as Activity


    Box(modifier = Modifier.fillMaxSize()) {
        SwipeRefresh(
            state = rememberSwipeRefreshState(isRefreshing.collectAsState().value),
            onRefresh = {
                mainViewModel.refresh()
                mainViewModel.getWABusinessStatusImage()
                mainViewModel.getWhatsappStatusImage()
            },
        ) {
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 128.dp)
            ) {
                savedStatus.value?.let { list ->
                    items(
                        items = list,
                        key = {
                            it.path
                        },
                        contentType = {
                            it.path
                        }
                    ) {
                        if (!it.isVideo && it.title.endsWith(".jpg")) {
                            ImageLayout(status = it, touchImageResource = R.drawable.share) {
                                shareFileIntentForImage(status = it, context = context)
                            }
                        } else {
                            VideoLayout(status = it, touchImageResource = R.drawable.share) {
                                shareFileIntentForVideo(status = it, context = context)
                            }
                        }
                    }
                }
            }
        }
    }
}

private fun shareFileIntentForImage(status: Status, context: Context) {

    val uri = FileProvider.getUriForFile(context, "com.example.statussaver.provider", status.file)
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "image/jpg"
    // .setDataAndType(uri, "image/jpg")
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.putExtra(Intent.EXTRA_STREAM, uri)

    try {
        context.startActivity(Intent.createChooser(intent, ""))
    } catch (ex: ActivityNotFoundException) {
        Toast.makeText(
            context,
            "Sorry, we cannot display that PDF!",
            Toast.LENGTH_LONG
        ).show()
    }

//    val takeFlags: Int = Intent.FLAG_GRANT_READ_URI_PERMISSION or
//            Intent.FLAG_GRANT_WRITE_URI_PERMISSION
//    val filePath = File(
//        Environment.getExternalStorageDirectory().path +
//                File.separator, "StatusSaver"
//    )
//    val newFilePath = File(filePath, status.file.absolutePath)
//
//
//    val imageUri = FileProvider.getUriForFile(
//        context,
//        "com.example.statussaver.provider",
//        newFilePath
//    )
//    Log.d("tagggg", "$imageUri")
//    val shareIntent = Intent(Intent.ACTION_SEND)
//    //   shareIntent.type =  "image/*"
//    shareIntent.setDataAndType(imageUri, "image/*")
//    shareIntent.putExtra(
//        Intent.EXTRA_STREAM,
//        imageUri
//    )
//    shareIntent.addFlags(takeFlags)
//    Log.d("file debug", "$imageUri")
//    context.startActivity(Intent.createChooser(shareIntent, "Share"))
}


private fun shareFileIntentForVideo(status: Status, context: Context) {
    val builder = StrictMode.VmPolicy.Builder()
    StrictMode.setVmPolicy(builder.build())
//    val filePath = File(
//        Environment.getExternalStorageDirectory().path +
//                File.separator, "StatusSaver"
//    )
//    val newFilePath = File(filePath, status.file.absolutePath)
//
//
//    val videoUri = FileProvider.getUriForFile(
//        context,
//        "com.example.statussaver.provider",
//        newFilePath
//    )
//    //
//    val shareIntent = Intent(Intent.ACTION_SEND)
//    shareIntent.type = "image/mp4"
//    shareIntent.putExtra(
//        Intent.EXTRA_STREAM,
//        videoUri
//    )
//    shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
//    context.startActivity(Intent.createChooser(shareIntent, "Share Status Saver Video"))

    val shareIntent = Intent(Intent.ACTION_SEND)

    if (status.isVideo) shareIntent.type = "image/mp4" else shareIntent.type = "image/jpg"

    shareIntent.putExtra(
        Intent.EXTRA_STREAM,
        Uri.parse("file://" + status.file.absolutePath)
    )
    context.startActivity(Intent.createChooser(shareIntent, "Share image"))
}