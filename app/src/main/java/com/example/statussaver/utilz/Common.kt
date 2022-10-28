package com.example.statussaver.utilz

import android.content.Context
import android.os.Environment
import android.util.Log
import android.widget.RelativeLayout
import com.example.statussaver.model.Status
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object Common {
    val STATUS_DIRECTORY = File(
        Environment.getExternalStorageDirectory().toString() +
                File.separator + "WhatsApp Business/Media/.Statuses"
    )
    val STATUS_DIRECTORY_NEW = File(
        (Environment.getExternalStorageDirectory().toString() +
                File.separator + "Android/media/com.whatsapp.w4b/WhatsApp Business/Media/.Statuses")
    )
    var APP_DIR: String? = null
    fun copyFile(status: Status, context: Context, container: RelativeLayout) {
        val file = File(APP_DIR)
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Snackbar.make(container, "Something went wrong", Snackbar.LENGTH_SHORT).show()
            }
        }
        val fileName: String
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val currentDateTime = sdf.format(Date())
        if (status.isVideo) {
            fileName = "VID_$currentDateTime.mp4"
        } else {
            fileName = "IMG_$currentDateTime.jpg"
        }
        val destFile = File(file.toString() + File.separator + fileName)
        try {
            org.apache.commons.io.FileUtils.copyFile(status.file, destFile)
            destFile.setLastModified(System.currentTimeMillis())
            SingleMediaScanner(context, file)
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("satusSaver", e.toString())
        }
    }

}