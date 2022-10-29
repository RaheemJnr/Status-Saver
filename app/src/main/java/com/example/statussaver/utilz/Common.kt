package com.example.statussaver.utilz

import android.content.Context
import android.util.Log
import android.widget.RelativeLayout
import com.example.statussaver.model.Status
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object Common {
    var APP_DIR: String? = null
    fun copyFile(status: Status, context: Context, container: RelativeLayout) {
        val file = APP_DIR?.let { File(it) }
        if (file != null) {
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    Snackbar.make(container, "Something went wrong", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        val fileName: String
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val currentDateTime = sdf.format(Date())
        fileName = if (status.isVideo) {
            "VID_$currentDateTime.mp4"
        } else {
            "IMG_$currentDateTime.jpg"
        }
        val destFile = File(file.toString() + File.separator + fileName)
        try {
            org.apache.commons.io.FileUtils.copyFile(status.file, destFile)
            destFile.setLastModified(System.currentTimeMillis())
            if (file != null) {
                SingleMediaScanner(context, file)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("satusSaver", e.toString())
        }
    }

}