package com.example.statussaver

import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.statussaver.model.Status
import com.example.statussaver.ui.theme.StatusSaverTheme
import com.example.statussaver.utilz.Common
import java.io.File
import java.util.*

class MainActivity : ComponentActivity() {

    private val imagesList: ArrayList<Status> = arrayListOf()
    private val handler = Handler()

    //
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StatusSaverTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {

                    Greeting("${imagesList.size}") {
                        getStatus()
                    }
                }
            }
        }
    }

    private fun getStatus() {
        if (Common.STATUS_DIRECTORY.exists()) {
            execute(Common.STATUS_DIRECTORY)
        } else if (Common.STATUS_DIRECTORY_NEW.exists()) {
            execute(Common.STATUS_DIRECTORY_NEW)
        }
    }

    private fun execute(wAFolder: File) {
        Thread {
            val statusFiles: Array<File>? = wAFolder.listFiles()
            imagesList.clear()
            if (statusFiles != null && statusFiles.isNotEmpty()) {
                Arrays.sort(statusFiles)
                for (file in statusFiles) {
                    val status = Status(file, file.name, file.absolutePath)
                    if (!status.isVideo && status.title.endsWith(".jpg")) {
                        imagesList.add(status)
                    }
                }
                handler.post(Runnable {
                    if (imagesList.size <= 0) {
//                        messageTextView.setVisibility(View.VISIBLE)
//                        messageTextView.setText(R.string.no_files_found)
                    } else {
//                        messageTextView.setVisibility(View.GONE)
//                        messageTextView.setText("")
                    }
//                    imageAdapter = ImageAdapter(imagesList, container)
//                    recyclerView.setAdapter(imageAdapter)
//                    imageAdapter.notifyDataSetChanged()
//                    progressBar.setVisibility(View.GONE)
                })
            }
//            else {
////                handler.post {
//////                    progressBar.setVisibility(View.GONE)
//////                    messageTextView.setVisibility(View.VISIBLE)
//////                    messageTextView.setText(R.string.no_files_found)
//////                    Toast.makeText(
//////                        getActivity(),
//////                        getString(R.string.no_files_found),
//////                        Toast.LENGTH_SHORT
//////                    )
//////                        .show()
////                }
//            }
            //   swipeRefreshLayout.setRefreshing(false)
        }.start()
    }

}


@Composable
fun Greeting(name: String, onClick: () -> Unit) {
    Column {
        Text(text = "Hello $name!")

        Button(
            onClick = {
                onClick()
            })
        {
            Text(text = "Folder permission")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    StatusSaverTheme {
        Greeting("Android") {

        }
    }
}