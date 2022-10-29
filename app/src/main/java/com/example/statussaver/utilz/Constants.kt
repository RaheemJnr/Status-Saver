package com.example.statussaver.utilz

import android.Manifest
import android.os.Environment
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
        Environment.getExternalStorageDirectory().toString() +
                File.separator + "WhatsApp/Media/.Statuses"
    )

    val WHATSAPP_STATUS_DIRECTORY_NEW = File(
        (Environment.getExternalStorageDirectory().toString() +
                File.separator + "Android/media/com.whatsapp/WhatsApp/Media/.Statuses")
    )
}

