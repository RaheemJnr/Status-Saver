package com.example.statussaver.utilz

import android.Manifest


object Constants {
    const val REQUEST_PERMISSIONS = 1005

    val PERMISSIONS = arrayOf(
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    )

    const val MANAGE_EXTERNAL_STORAGE_PERMISSION = "android:manage_external_storage"
}

