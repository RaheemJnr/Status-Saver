package com.example.statussaver.utilz

import android.Manifest
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.os.Environment
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.statussaver.model.Status
import java.io.File


object Constants {
    const val REQUEST_PERMISSIONS = 1005

    val PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    const val MANAGE_EXTERNAL_STORAGE_PERMISSION = "android:manage_external_storage"

    //
   val BUSINESS_STATUS_DIRECTORY = File(
        Environment.getExternalStorageDirectory().toString() + File.separator + "WhatsApp Business/Media/.Statuses")

    val BUSINESS_STATUS_DIRECTORY_NEW = File(
        Environment.getExternalStorageDirectory().toString() +
                File.separator + "Android/media/com.whatsapp.w4b/WhatsApp Business/Media/.Statuses"
    )

    val WHATSAPP_STATUS_DIRECTORY = File(
        Environment.getExternalStorageDirectory().toString() + File.separator + "WhatsApp/Media/.Statuses"
    )

    val WHATSAPP_STATUS_DIRECTORY_NEW = File(
        (Environment.getExternalStorageDirectory().toString() + File.separator + "Android/media/com.whatsapp/WhatsApp/Media/.Statuses")
    )
}


data class TabItems(
    val value: String,
)


fun viewImage(context: Activity, status: Status) {
    val uri = FileProvider.getUriForFile(
        context,
        "com.example.statussaver.provider",
        status.file
    )
    val intent = Intent(Intent.ACTION_VIEW, uri)
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    context.startActivity(intent)
}
//
fun shareFileIntentForImage(status: Status, context: Context) {

    val uri = FileProvider.getUriForFile(context, "com.example.statussaver.provider", status.file)
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "image/*"
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.putExtra(Intent.EXTRA_STREAM, uri)

    try {
        context.startActivity(Intent.createChooser(intent, ""))
    } catch (ex: ActivityNotFoundException) {
        Toast.makeText(
            context,
            "Sorry, we cannot display this image!",
            Toast.LENGTH_LONG
        ).show()
    }
}
//
fun shareFileIntentForVideo(status: Status, context: Context) {
    val uri = FileProvider.getUriForFile(context, "com.example.statussaver.provider", status.file)
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "image/*"
    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    intent.putExtra(Intent.EXTRA_STREAM, uri)

    try {
        context.startActivity(Intent.createChooser(intent, ""))
    } catch (ex: ActivityNotFoundException) {
        Toast.makeText(
            context,
            "Sorry, we cannot display this image!",
            Toast.LENGTH_LONG
        ).show()
    }
}