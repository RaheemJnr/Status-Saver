package com.example.statussaver.utilz

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Environment
import android.widget.RelativeLayout
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import com.example.statussaver.R
import com.example.statussaver.model.Status
import com.google.android.material.snackbar.Snackbar
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

object Common {
    val MINI_KIND = 1
    val MICRO_KIND = 3
    val GRID_COUNT = 2
    private val CHANNEL_NAME = "GAUTHAM"
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
            org.apache.commons.io.FileUtils.copyFile(status.getFile(), destFile)
            destFile.setLastModified(System.currentTimeMillis())
            SingleMediaScanner(context, file)
            showNotification(context, container, destFile, status)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun showNotification(
        context: Context,
        container: RelativeLayout,
        destFile: File,
        status: Status
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            makeNotificationChannel(context)
        }
        val data = FileProvider.getUriForFile(
            context,
            "a.gautham.statusdownloader" + ".provider",
            File(destFile.absolutePath)
        )
        val intent = Intent(Intent.ACTION_VIEW)
        if (status.isVideo()) {
            intent.setDataAndType(data, "video/*")
        } else {
            intent.setDataAndType(data, "image/*")
        }
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
        val notification = NotificationCompat.Builder(context, CHANNEL_NAME)
        notification.setSmallIcon(R.drawable.ic_file_download_black)
            .setContentTitle(destFile.name)
            .setContentText("File Saved to" + APP_DIR)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
        val notificationManager: NotificationManager? =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        assert(notificationManager != null)
        notificationManager!!.notify(Random().nextInt(), notification.build())
        Snackbar.make(container, "Saved to " + APP_DIR, Snackbar.LENGTH_LONG).show()
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun makeNotificationChannel(context: Context) {
        val channel =
            NotificationChannel(CHANNEL_NAME, "Saved", NotificationManager.IMPORTANCE_DEFAULT)
        channel.setShowBadge(true)
        val notificationManager: NotificationManager? =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        assert(notificationManager != null)
        notificationManager!!.createNotificationChannel(channel)
    }
}