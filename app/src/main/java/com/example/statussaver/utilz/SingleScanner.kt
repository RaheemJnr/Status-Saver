package com.example.statussaver.utilz

import android.content.Context
import android.media.MediaScannerConnection
import android.media.MediaScannerConnection.MediaScannerConnectionClient
import android.net.Uri
import java.io.File

class SingleMediaScanner(context: Context?, private val mFile: File) :
    MediaScannerConnectionClient {
    private val mediaScannerConnection: MediaScannerConnection

    init {
        mediaScannerConnection = MediaScannerConnection(context, this)
        mediaScannerConnection.connect()
    }

    override fun onMediaScannerConnected() {
        mediaScannerConnection.scanFile(mFile.absolutePath, null)
    }

    override fun onScanCompleted(path: String, uri: Uri) {
        mediaScannerConnection.disconnect()
    }
}