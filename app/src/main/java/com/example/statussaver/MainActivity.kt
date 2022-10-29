package com.example.statussaver

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.storage.StorageManager
import android.provider.DocumentsContract
import android.provider.OpenableColumns
import android.text.TextUtils.replace
import android.util.Log
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
import androidx.documentfile.provider.DocumentFile
import com.example.statussaver.ui.theme.StatusSaverTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Document

class MainActivity : ComponentActivity() {

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
                    Greeting("hi") {

                    }
                }
            }
        }
    }


/*
override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
super.onActivityResult(requestCode, resultCode, data)
if (requestCode == 1234 && resultCode == Activity.RESULT_OK) {
val directoryUri = data?.data ?: return

contentResolver.takePersistableUriPermission(
directoryUri,
Intent.FLAG_GRANT_READ_URI_PERMISSION
)

val documentsTree = DocumentFile.fromTreeUri(application, directoryUri) ?: return
val childDocuments = documentsTree.listFiles().toCachingList()
treedata = childDocuments.toString()
Log.d("whatsapp", treedata)

//                // It's much nicer when the documents are sorted by something, so we'll sort the documents
//                // we got by name. Unfortunate there may be quite a few documents, and sorting can take
//                // some time, so we'll take advantage of coroutines to take this work off the main thread.
//                CoroutineScope().launch {
//                    val sortedDocuments = withContext(Dispatchers.IO) {
//                        childDocuments.toMutableList().apply {
//                            sortBy { it.name }
//                        }
//                    }
//                    _documents.postValue(sortedDocuments)
//                }
}
}
*/
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