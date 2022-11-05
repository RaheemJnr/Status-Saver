package com.example.statussaver.utilz

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.statussaver.model.Status
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object Common {
    var APP_DIR: String? = null
    fun saveFile(status: Status, context: Context) {
        val file = APP_DIR?.let { File(it) }
        if (file != null) {
            if (!file.exists()) {
                if (!file.mkdirs()) {
                    Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show()
                }
            }
        }
        val fileName: String
        val sdf = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
        val currentDateTime = sdf.format(Date())
        fileName = if (status.isVideo) {
            "STATUS_VID_$currentDateTime.mp4"
        } else {
            "STATUS_IMG_$currentDateTime.jpg"
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
            Log.e("statusSaver", e.toString())
        }
    }

}